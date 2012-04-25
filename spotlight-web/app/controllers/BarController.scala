package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.libs.json.Json._
import io.Source
import java.text.SimpleDateFormat
import spotlight.engine.YahooDailyConverter

object BarController extends Controller {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def findAll = Action {
    new YahooDailyConverter()

    val source = Source.fromFile("/home/michael/spy.csv")
//    val spyBars = source.getLines().toList.tail.map(_.split(",")).map(contents => {
//      Bar(
//        date = sdf.parse(contents(1)),
//        open = BigDecimal(contents(2)),
//        high = BigDecimal(contents(3)),
//        low = BigDecimal(contents(4)),
//        close = BigDecimal(contents(5)),
//        volume = contents(6).toLong
//      )
//    })

    val spyBars = source.getLines().toList.tail.map(_.split(",")).map(contents => {
      Map(
        "date" -> toJson(contents(1)),
        "open" -> toJson(contents(2)),
        "high" -> toJson(contents(3)),
        "low" -> toJson(contents(4)),
        "close" -> toJson(contents(5)),
        "volume" -> toJson(contents(6))
      )
    }).filter(map => map("date").toString().contains("2012"))

//                   var count = 0
//    val spyBars = source.getLines().toList.tail.map(_.split(",")).filter(_(1).contains("2012"))
//      .map(contents => {
//      count = count + 1
//      Map(
//        count.toString -> toJson(contents(5))
//      )
//    })
    source.close()

    Ok(toJson(spyBars))
  }
}
