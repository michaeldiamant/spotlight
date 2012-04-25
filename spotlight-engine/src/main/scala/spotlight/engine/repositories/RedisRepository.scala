package spotlight.engine.repositories

import redis.clients.jedis.Jedis

trait Redis {

  val client: Jedis
}
