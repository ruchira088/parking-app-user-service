package utils

import scala.concurrent.Future

object ScalaUtils
{
  def predicate(boolean: Boolean, exception: => Exception): Future[Unit] =
    if (boolean) Future.successful((): Unit) else Future.failed(exception)
}
