package utils

import exceptions.JsonDeserializationException
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.mvc.Request

import scala.util.{Failure, Success, Try}

object JsonUtils
{
  implicit def dateTimeObjectFormat: Format[DateTime] = new Format[DateTime]
  {
    override def reads(jsValue: JsValue): JsResult[DateTime] = jsValue match
      {
        case JsString(string) =>
          ParseUtils.dateTime(string)
            .fold(
              throwable => JsError(throwable.getMessage),
              JsSuccess(_)
            )
        case _ => JsError()
      }

    override def writes(dateTime: DateTime): JsValue =
      JsString(dateTime.toString)
  }

  def deserialize[A](implicit request: Request[JsValue], reads: Reads[A]): Try[A] =
    request.body.validate[A].fold[Try[A]](
      validationErrors => Failure(JsonDeserializationException(validationErrors)),
      Success(_)
    )
}