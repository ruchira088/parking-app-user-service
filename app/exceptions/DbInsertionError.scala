package exceptions

case class DbInsertionError[A](item: A) extends Exception