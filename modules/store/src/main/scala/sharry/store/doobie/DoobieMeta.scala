package sharry.store.doobie

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate}

import sharry.common._
import sharry.common.syntax.all._

import doobie._
import doobie.implicits.legacy.instant._
import doobie.util.log.Success
import io.circe.{Decoder, Encoder}
import scodec.bits.ByteVector

trait DoobieMeta {

  implicit val sqlLogging = DoobieMeta.DefaultLogging.handler

  def jsonMeta[A](implicit d: Decoder[A], e: Encoder[A]): Meta[A] =
    Meta[String].imap(str => str.parseJsonAs[A].fold(ex => throw ex, identity))(a =>
      e.apply(a).noSpaces
    )

  implicit val metaUserState: Meta[AccountState] =
    Meta[String].timap(AccountState.unsafe)(AccountState.asString)

  implicit val metaAccountSource: Meta[AccountSource] =
    Meta[String].timap(AccountSource.unsafe)(_.name)

  implicit val metaPassword: Meta[Password] =
    Meta[String].timap(Password(_))(_.pass)

  implicit val metaIdent: Meta[Ident] =
    Meta[String].timap(Ident.unsafe)(_.id)

  implicit val ciIdentMeta: Meta[CIIdent] =
    metaIdent.timap(CIIdent.apply)(_.value)

  implicit val metaTimestamp: Meta[Timestamp] =
    Meta[Instant].imap(Timestamp(_))(_.value)

  implicit val metaLocalDate: Meta[LocalDate] =
    Meta[String].timap(str => LocalDate.parse(str))(_.format(DateTimeFormatter.ISO_DATE))

  implicit val metaDuration: Meta[Duration] =
    Meta[Long].timap(n => Duration.seconds(n))(_.seconds)

  implicit val metaByteSize: Meta[ByteSize] =
    Meta[Long].timap(n => ByteSize(n))(_.bytes)

  implicit val byteVectorMeta: Meta[ByteVector] =
    Meta[String].timap(s => ByteVector.fromValidHex(s))(_.toHex)
}

object DoobieMeta extends DoobieMeta {
  private[this] val logger = sharry.logging.unsafeLogger("DoobieMeta")

  object TraceLogging {
    implicit val handler =
      LogHandler {
        case e @ Success(_, _, _, _) =>
          logger.trace("SQL success: " + e)
        case e =>
          logger.trace(s"SQL failure: $e")
      }
  }

  object DefaultLogging {
    implicit val handler =
      LogHandler {
        case e @ Success(_, _, _, _) =>
          logger.trace("SQL success: " + e)
        case e =>
          logger.warn(s"SQL failure: $e")
      }
  }
}
