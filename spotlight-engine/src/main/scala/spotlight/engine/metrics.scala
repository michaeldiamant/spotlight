package spotlight.engine

import akka.actor.{Props, ActorSystem, Actors, Actor}


class MetricActor extends Actor {

  protected def receive = {
    case request: MetricRequest[_] => sender ! request.process
  }
}

case class MetricRequest[R](f: () => R) {

  def process: R = f()
}
