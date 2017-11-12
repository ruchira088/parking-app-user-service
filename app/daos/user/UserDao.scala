package daos.user

import controllers.requests.bodies.CreateUserRequest
import exceptions.DbInsertionError
import models.User
import services.password.hashing.PasswordHashingService
import types.FutureO
import utils.ScalaUtils

import scala.concurrent.{ExecutionContext, Future}

trait UserDao
{
  protected def passwordHashingService: PasswordHashingService

  protected def insert(user: User): Future[Int]

  def getByUsername(username: String): FutureO[User]

  def insert(createUserRequest: CreateUserRequest)(implicit executionContext: ExecutionContext): Future[User] = for
    {
      hashedPassword <- passwordHashingService.hashPassword(createUserRequest.password)
      user = User.create(createUserRequest.username, createUserRequest.email, hashedPassword)

      result <- insert(user)
      _ <- ScalaUtils.predicate(result == 1, DbInsertionError(user))
    }
    yield user
}
