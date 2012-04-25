package remote

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import com.weiglewilczek.slf4s.Logging

object EngineDispatchingActor extends Logging {

  private lazy val system = ActorSystem(
    name = "engineDispatcher",
    config = ConfigFactory.load.getConfig("engineLookup")
  )
  lazy val engine = system.actorFor("akka://spotlightEngineSys@127.0.0.1:2552/user/spotlightEngine")

  def shutdown() {
    logger.info("Shutting down engine dispatching actor.")

    system.shutdown()
  }
}
