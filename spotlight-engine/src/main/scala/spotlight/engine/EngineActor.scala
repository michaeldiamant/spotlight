package spotlight.engine

import akka.actor.Actor
import com.weiglewilczek.slf4s.Logging
import model.CorrelationRequest

class EngineActor extends Actor with Logging {

  private val computations = new Computations

  def receive = {
    case request: CorrelationRequest => {
      logger.info("Processing [" + request + " ]")

      sender ! computations.correlation(request)
    }
    case unknown => logger.error("Unknown request:  " + unknown)
  }
}
