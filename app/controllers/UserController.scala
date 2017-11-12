package controllers

import javax.inject.{Inject, Singleton}

import controllers.requests.bodies.{AuthenticateUserRequest, CreateUserRequest}
import daos.user.UserDao
import play.api.libs.json.JsValue
import play.api.mvc._
import services.AuthenticationService
import utils.JsonUtils

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(
        userDao: UserDao,
        authenticationService: AuthenticationService,
        parsers: PlayBodyParsers,
        controllerComponents: ControllerComponents
)(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents)
{
  def create(): Action[JsValue] = Action.async(parsers.json)
    {
      implicit request: Request[JsValue] => for
        {
          createUserRequest <- Future.fromTry(JsonUtils.deserialize[CreateUserRequest])
          user <- userDao.insert(createUserRequest)
        }
        yield Ok(user.toSanitizedJson)
    }

  def authenticate(): Action[JsValue] = Action.async(parsers.json)
    {
      implicit request: Request[JsValue] => for
        {
          authenticationRequest <- Future.fromTry(JsonUtils.deserialize[AuthenticateUserRequest])
          user <- authenticationService.authenticate(authenticationRequest).flatten
        }
        yield Ok(user.toSanitizedJson)
    }

}
