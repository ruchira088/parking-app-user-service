package controllers.requests.bodies

import play.api.libs.json.{Json, OFormat}

case class CreateUserRequest(
   username: String,
   password: String,
   email: String
)

object CreateUserRequest
{
  implicit def objectFormat: OFormat[CreateUserRequest] = Json.format[CreateUserRequest]
}