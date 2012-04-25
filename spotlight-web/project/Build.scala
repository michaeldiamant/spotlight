import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "spotlight-web"
  val appVersion = "0.0.1-SNAPSHOT"

  val appDependencies = Seq(
    "redis.clients" % "jedis" % "2.0.0",
    "org.apache.commons" % "commons-math" % "2.2",
    "com.typesafe.akka" % "akka-kernel" % "2.0",
    "com.typesafe.akka" % "akka-actor" % "2.0",
    "com.typesafe.akka" % "akka-remote" % "2.0"
  )

  val engine = Project("spotlight-engine", file("modules/spotlight-engine"))

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
  ) dependsOn(engine)
}
