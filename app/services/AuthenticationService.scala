package services

import javax.inject.{Inject, Singleton}

import controllers.requests.bodies.AuthenticateUserRequest
import daos.user.UserDao
import exceptions.IncorrectCredentialsException
import models.User
import services.password.hashing.PasswordHashingService
import types.FutureO
import utils.ScalaUtils

import scala.concurrent.ExecutionContext

@Singleton
class AuthenticationService @Inject()(passwordHashingService: PasswordHashingService, userDao: UserDao)(implicit executionContext: ExecutionContext)
{
  def authenticate(authenticateUserRequest: AuthenticateUserRequest): FutureO[User] = for
    {
      user <- userDao.getByUsername(authenticateUserRequest.username)

      success <- passwordHashingService.checkPassword(authenticateUserRequest.password, user.hashedPassword)

      _ <- ScalaUtils.predicate(success, IncorrectCredentialsException)
    }
    yield user
}
