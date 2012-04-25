package spotlight.engine

import akka.actor.{ActorSystem, Actor}
import akka.pattern.ask
import akka.dispatch.{Await, Promise}
import akka.util.duration._
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

object MetricRequestingActor extends App {

  val remoteActor = ActorSystem("Lol", ConfigFactory.load.getConfig("remoteLookup")).actorFor("akka://spotlightEngine@127.0.0.1:2552/user/simpleEngine")

  implicit val timeout = new Timeout(2 seconds)
  Thread.sleep(1000)

  remoteActor ! "hi"
  
//  val x = (remoteActor ? "hi").mapTo[String]
//
//  val result = Await.result(x, 5 seconds)
//          println(">>> " + result)
}
