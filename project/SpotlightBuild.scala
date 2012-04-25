import sbt._
import Keys._
import PlayProject._

object SpotlightBuild extends Build {

  lazy val root = Project(id = "spotlight", base = file(".")) aggregate(engine)

  val engine = Project(id = "spotlight-engine", base = file("spotlight-engine"))

// Aggregating PlayProjects at the root level is not yet supported.
//  val web = PlayProject(
//    "spotlight-web", "0.0.1-SNAPSHOT", path = file("spotlight-web")
//  ).dependsOn(engine)
}

