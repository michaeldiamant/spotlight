import play.api.{Application, GlobalSettings}
import remote.EngineDispatchingActor

object Global extends GlobalSettings {

  override def onStop(app: Application) {
    EngineDispatchingActor.shutdown()
  }
}
