package controllers

import javax.inject._

import io.circe.Printer
import io.circe.generic.auto._
import io.circe.syntax._
import play.api.libs.circe.Circe
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe {
  implicit val codec = Codec.utf_8
  implicit val customPrinter = Printer.spaces2.copy(dropNullValues = false)

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = {
    Action { request =>
      Ok(s"sad:${request.session.get("ha")}").withSession("ha" -> "4356asd")
    }
  }

  def echo(ha: String) = Action { request =>
    Ok(s"got it,$request,ha:$ha")
  }

  def clientId(id: Long) = Action { request =>
    Ok(s"id:$id,clazz:${id.getClass}")
  }

  def testJson = Action(circe.json[Foo]) { request =>
    Ok(request.body.asJson.spaces2).as(JSON)
  }

  def testView = Action {
    Ok(views.html.test("sadsad",List("1123","2343243","asdasdasdsa","蒋航")))
  }
}

case class Foo(name: String, ids: Array[Long])