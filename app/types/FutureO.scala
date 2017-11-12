package types

import exceptions.EmptyOptionException

import scala.concurrent.{ExecutionContext, Future}

case class FutureO[A](future: Future[Option[A]])
{
  def flatMap[B](func: A => FutureO[B])(implicit executionContext: ExecutionContext): FutureO[B] =
    FutureO {
      future.flatMap {
        case Some(value) => func(value).future
        case None => Future.successful(None)
      }
    }

  def map[B](func: A => B)(implicit executionContext: ExecutionContext): FutureO[B] =
    FutureO { future.map( _ map func) }

  def flatten(implicit executionContext: ExecutionContext): Future[A] =
    future.flatMap {
      case Some(value) => Future.successful(value)
      case None => Future.failed(EmptyOptionException)
    }
}

object FutureO
{
  implicit def fromFuture[A](future: Future[A])(implicit executionContext: ExecutionContext): FutureO[A] =
    FutureO {
      future.map(Some(_))
    }
}