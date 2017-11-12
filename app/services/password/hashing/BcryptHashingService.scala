package services.password.hashing

import javax.inject.{Inject, Singleton}

import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.{ExecutionContext, Future, blocking}

@Singleton
class BcryptHashingService @Inject()(implicit executionContext: ExecutionContext)
  extends PasswordHashingService
{
  override def hashPassword(password: String) =
    blockingFuture(BCrypt.hashpw(password, BCrypt.gensalt()))

  override def checkPassword(passwordCandidate: String, hashedPassword: String) =
    blockingFuture(BCrypt.checkpw(passwordCandidate, hashedPassword))

  private def blockingFuture[A](body: => A): Future[A] =
    Future {
      blocking {
        body
      }
    }
}
