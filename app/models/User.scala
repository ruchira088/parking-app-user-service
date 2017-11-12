package models

import org.joda.time.DateTime
import play.api.libs.json.{JsObject, Json, OFormat}
import utils.GeneralUtils.generateUuid
import utils.JsonUtils.dateTimeObjectFormat

case class User(
    id: String,
    createdAt: DateTime,
    username: String,
    hashedPassword: String,
    email: String,
    mobileNumber: Option[String]
) {
  user =>

  def toSanitizedJson: JsObject = Json.toJsObject(user.copy(hashedPassword = "*****"))
}

object User
{
  implicit def objectFormat: OFormat[User] = Json.format[User]

  def create(username: String, email: String, hashedPassword: String) =
    User(generateUuid(), DateTime.now(), username, hashedPassword, email, None)
}