package spotlight.engine

import com.typesafe.config.ConfigFactory
import akka.actor.{Props, ActorSystem}


object SpotlightEngineRunner extends App {

  val system = ActorSystem("spotlightEngineSys", ConfigFactory.load.getConfig("engine"))
  val engine = system.actorOf(Props[EngineActor], "spotlightEngine")
}
