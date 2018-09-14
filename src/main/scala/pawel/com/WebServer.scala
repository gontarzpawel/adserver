package pawel.com

import akka.http.scaladsl.server.{HttpApp, Route}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object WebServer extends HttpApp {

  import akka.actor.ActorSystem
  import akka.stream.ActorMaterializer
  import akka.util.Timeout

  implicit val timeout: Timeout = Timeout.durationToTimeout(20 seconds)
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  private val store: AdStore = InMemoryAdStore
  private val adsMatchUseCase = new AdsMatchUseCase(store)

  override protected def routes: Route = new AdsApi(adsMatchUseCase).route

  override protected def postHttpBindingFailure(cause: Throwable): Unit = {
    println(s"The server could not be started due to $cause")
  }

}

object Boot extends App with LazyLogging {

  def run(): Unit = WebServer.startServer("localhost", 8080)

  run()
}