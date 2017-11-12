package exceptions

import play.api.libs.json.{JsPath, JsonValidationError}

case class JsonDeserializationException(errors: List[(JsPath, List[JsonValidationError])]) extends Exception

object JsonDeserializationException
{
  def apply(errors: Seq[(JsPath, Seq[JsonValidationError])]): JsonDeserializationException =
    JsonDeserializationException(
      errors.toList.map { case (jsPath, validationErrors) => (jsPath, validationErrors.toList) }
    )
}
