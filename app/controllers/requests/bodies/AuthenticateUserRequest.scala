package controllers.requests.bodies

import play.api.libs.json.{Json, OFormat}

case class AuthenticateUserRequest(username: String, password: String)

object AuthenticateUserRequest
{
  implicit def objectFormat: OFormat[AuthenticateUserRequest] = Json.format[AuthenticateUserRequest]
}
