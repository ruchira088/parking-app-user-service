package utils

import java.util.UUID

object GeneralUtils
{
  def generateUuid(): String = UUID.randomUUID().toString
}
