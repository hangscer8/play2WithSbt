package util.actor

import akka.actor.{Actor, ActorLogging, Props}

class ActorSupervisor extends Actor with ActorLogging {
  override def receive: Receive = {
    case p: Props =>
      sender() ! context.actorOf(p)
    case (p: Props, name: String) =>
      sender() ! context.actorOf(p, name)
  }
}