package daos.user

import java.sql.{Date, Timestamp}
import javax.inject.{Inject, Singleton}

import models.User
import org.joda.time.DateTime
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import services.password.hashing.PasswordHashingService
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape
import types.FutureO

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserSlickDao @Inject()(
        protected val dbConfigProvider: DatabaseConfigProvider,
        protected val passwordHashingService: PasswordHashingService
)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] with UserDao
{
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, UserSlickDao.TABLE_NAME)
  {
    implicit val dateTimeColumnType = MappedColumnType.base[DateTime, Timestamp](
      jodaTime => new Timestamp(jodaTime.getMillis), new DateTime(_)
    )

    def id = column[String]("id")
    def createdAt = column[DateTime]("created_at")
    def username = column[String]("username")
    def password = column[String]("hashed_password")
    def email = column[String]("email")
    def mobileNumber = column[Option[String]]("mobile_number")

    def pk = primaryKey("pk", (id, createdAt))

    override def * : ProvenShape[User] =
      (id, createdAt, username, password, email, mobileNumber) <> ((User.apply _).tupled, User.unapply)
  }

  def users = TableQuery[UserTable]

  override def insert(user: User): Future[Int] = db.run(users += user)

  override def getByUsername(username: String) =
    FutureO {
      db.run(
        users.filter(_.username === username).result.headOption
      )
    }
}

object UserSlickDao
{
  val TABLE_NAME = "users"
}
