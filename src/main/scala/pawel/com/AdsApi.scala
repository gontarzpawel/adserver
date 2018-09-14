package pawel.com

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.Materializer
import com.typesafe.scalalogging.LazyLogging
import io.circe.Encoder
import io.circe.syntax._
import pawel.com.AdsApi.AdResponse
import pawel.com.domain.Rule.Gender

import scala.concurrent.ExecutionContext

class AdsApi(adsMatchUseCase: AdsMatchUseCase)
            (implicit executionContext: ExecutionContext,
             materializer: Materializer,
             actorSystem: ActorSystem)
  extends Directives with LazyLogging {

  import AdsApi.encoders.adResponseEncoder

  val route: Route =
    logRequestResult("ad_service", Logging.InfoLevel) {
      pathPrefix("ad") {
        parameter("age".as[Int]) { age =>
          parameter("gender".as[String]) { gender =>
            get {
              onSuccess(
                adsMatchUseCase.getMatchingAd(age, Gender.withNameInsensitive(gender))
              ) {
                case Some(ad) => complete((StatusCodes.OK, AdResponse(Some(ad.id)).asJson.noSpaces))
                case None => complete((StatusCodes.OK, AdResponse(None).asJson.noSpaces))
              }
            }
          }
        }
      }
    }
}

object AdsApi {

  final case class AdResponse(id: Option[String]) extends AnyVal

  object encoders {
    import io.circe.generic.semiauto
    implicit val adResponseEncoder: Encoder[AdResponse] = semiauto.deriveEncoder
  }

}