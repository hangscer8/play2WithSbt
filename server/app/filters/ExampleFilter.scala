package filters

import java.util.Date
import javax.inject._

import play.api.mvc._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/**
  * This is a simple filter that adds a header to all requests. It's
  * added to the application's list of filters by the
  * [[Filters]] class.
  *
  * @param ec This class is needed to execute code asynchronously.
  *           It is used below by the `map` method.
  */
@Singleton
class ExampleFilter @Inject()(implicit ec: ExecutionContext) extends EssentialFilter {

  import util.actor.ActorMain._

  override def apply(next: EssentialAction) = EssentialAction { request =>
    next(request).map { result =>
      result.body.dataStream.runFold("")((a, z) => a + z.utf8String).onComplete {
        case Success(body) =>

          println(s"timestamp:${new Date(System.currentTimeMillis())} , method:${Console.RED}${request.method.toUpperCase}${Console.RESET} , path:${Console.GREEN}${request.path}${Console.RESET} , session:${Console.BLUE}${request.session}${Console.RESET} , flash:${Console.MAGENTA}${request.flash}${Console.RESET}")
        case Failure(ex) =>
      }
      result.withHeaders("X-ExampleFilter" -> "foo")
    }
  }
}