package modules

import com.google.inject.AbstractModule
import daos.user.{UserDao, UserSlickDao}
import services.password.hashing.{BcryptHashingService, PasswordHashingService}

class GuiceBindingsModule extends AbstractModule
{
  override def configure() =
  {
    bind(classOf[UserDao]).to(classOf[UserSlickDao])
    bind(classOf[PasswordHashingService]).to(classOf[BcryptHashingService])
  }
}
