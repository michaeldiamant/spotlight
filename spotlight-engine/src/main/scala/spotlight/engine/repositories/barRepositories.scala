package spotlight.engine.repositories

import collection.immutable.Seq
import spotlight.engine.model.Bar
import scala.collection.JavaConversions._
import scalaz._
import Scalaz._
import redis.clients.jedis.Jedis

trait BarRepository {

  def findAll(symbol: String): Seq[Bar]

}

trait RedisBarRepository extends BarRepository with Redis {

  def findAll(symbol: String): Seq[Bar] = {

    def toValidation(xs: List[String]): Validation[String, String] =
      xs match {
        case Nil => Failure("Symbol " + symbol + " not found.")
        case x :: Nil => Success(x)
        case x :: xs => Failure("Multiple symbols with identifier " + symbol + " exist.")
      }

    //    key.map(client.get("instrument:"))

    def toSymbolValidation(value: String): Validation[String, String] = {
      value match {
        case `symbol` => value.success
        case _ => (value + " does not match " + symbol + ".").fail
      }
    }

    def toSymbolId(key: String): Validation[String, String] = {
      key.substring(key.indexOf(":") + 1, key.lastIndexOf(":")).parseLong.fold(
        exception => Failure(exception.getMessage),
        long => {
          Success(long.toString)
        }
      )
    }

    val xs = client.keys("instrument:*:name").toList.map(key => {
      for {
        symbol <- toSymbolValidation(key)
        symbolId <- toSymbolId(key)        
      } yield symbolId
    })

    
    xs.foreach(_.foreach(id =>
    {
      client.zrange("daily:" + id + ":open", 1, 10).toList.foreach(println(_))
    }))

    Seq()
  }
}

object RepoApp extends App {
  val jedis = new Jedis("localhost", 6379)
  jedis.connect()

  try {
    val repo = new RedisBarRepository {
      val client = jedis

    }
    repo.findAll("spy")

  } finally {
    jedis.disconnect()
  }
}
