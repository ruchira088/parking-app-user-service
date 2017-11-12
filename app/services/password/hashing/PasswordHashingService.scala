package services.password.hashing

import scala.concurrent.Future

trait PasswordHashingService
{
  def hashPassword(password: String): Future[String]

  def checkPassword(passwordCandidate: String, hashedPassword: String): Future[Boolean]
}
