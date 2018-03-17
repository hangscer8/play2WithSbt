package util.actor

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer

object ActorMain {
  implicit val system = ActorSystem("itRecruit")
  implicit val ec = system.dispatcher
  implicit val mat = ActorMaterializer()
  val supervisorActor = system.actorOf(Props[ActorSupervisor], "actorSupervisor")
}