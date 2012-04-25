package spotlight.engine

import io.Source
import redis.clients.jedis.Jedis
import java.util.Calendar

object RedisExample {

  def main(args: Array[String]) {
    val converter = new YahooDailyConverter().convert(_)
    val source = Source.fromFile("/home/michael/spy.csv")
    val bars = source.getLines().toList.tail.map(_.split(",")).map(contents => converter(contents))

    val redis = new Jedis("localhost", 6379)

    redis.connect()
    redis.setnx("global:instrument:uid", "0")
    val uid = redis.incr("global:instrument:uid")
    redis.setnx("instrument:" + uid + ":name", "spy")

    redis.setnx("global:daily:uid", "0")
    bars.foreach(bar => {
      val time = bar.date.getTime
      redis.zadd("daily:" + uid + ":open", time.toDouble, bar.open.toString())
      redis.zadd("daily:" + uid + ":high", time.toDouble, bar.high.toString())
      redis.zadd("daily:" + uid + ":low", time.toDouble, bar.low.toString())
      redis.zadd("daily:" + uid + ":close", time.toDouble, bar.close.toString())
      redis.zadd("daily:" + uid + ":volume", time.toDouble, bar.volume.toString)
      redis.zadd("daily:" + uid + ":time", time.toDouble, time.toString)
    })

    redis.disconnect()
  }
}
