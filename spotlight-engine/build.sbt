name := "spotlight-engine"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
    "redis.clients" % "jedis" % "2.0.0",
    "org.apache.commons" % "commons-math" % "2.2",
    "com.typesafe.akka" % "akka-kernel" % "2.0",
    "com.typesafe.akka" % "akka-actor" % "2.0",
    "org.scalaz" %% "scalaz-core" % "6.0.4",
    "com.typesafe.akka" % "akka-remote" % "2.0",
    "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.7",
     "ch.qos.logback" % "logback-classic" % "0.9.28"
)

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
