package controllers

import play.api.mvc._
import remote.EngineDispatchingActor
import play.api.libs.concurrent.AkkaFuture
import java.util.Date
import play.api.libs.json.Json._
import akka.util.Timeout
import akka.util.Duration
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat
import akka.pattern.ask
import spotlight.engine.model.{CorrelationResult, CorrelationRequest}

object ChartController extends Controller {

  private implicit val timeout = Timeout(Duration(10, TimeUnit.SECONDS))
  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def charts = Action {
    MethodNotAllowed("Unsupported")
  }

  def chart(chartId: String) = Action {
    val engine = EngineDispatchingActor.engine
    Async {
      val request = CorrelationRequest(symbol1 = "SPY", symbol2 = "UGA", rangeInDays = 30)
      new AkkaFuture((engine ? request).mapTo[CorrelationResult])
        .asPromise.map(result => {
        val jsonCorrelations = result.data.map {
          case (date, correlation) => Map(
            "date" -> toJson(sdf.format(date)),
            "correlation" -> toJson(correlation)
          )
        }

        val chart = Map(
          "title" -> toJson("Correlation " + result.symbol1 + " vs " + result.symbol2),
          "data" -> toJson(jsonCorrelations)
        )

        Ok(toJson(chart))
      })
    }
  }
}
