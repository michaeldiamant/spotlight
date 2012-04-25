package controllers

import play.api.mvc.Controller
import play.api.mvc.Action
import io.Source
import java.io.File
import java.text.SimpleDateFormat
import spotlight.engine.model.Bar
import java.util.Calendar
import scala.math.BigDecimal.RoundingMode._
import play.api.libs.json.Json._

object IndustryController extends Controller {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def findAll = Action {
    val csvs = new File("/home/michael/data/").listFiles().filter(_.getName.endsWith("csv"))
    val symbolToLines = csvs.map(csv => {
      val lines = Source.fromFile(csv).getLines().toList.tail
      val bars = lines.map(_.split(",")).map(contents => {
        Bar(
          date = sdf.parse(contents(1)),
          open = BigDecimal(contents(2)),
          high = BigDecimal(contents(3)),
          low = BigDecimal(contents(4)),
          close = BigDecimal(contents(5)),
          volume = contents(6).toLong
        )
      })

      val filteredBars = bars.filter(bar => {
        val cal = Calendar.getInstance()
        cal.setTime(bar.date)
        cal.get(Calendar.YEAR) == 2012
      })

      val todayBar = filteredBars.last
      val comparisonBar = filteredBars.takeRight(30).head

      println("today = " + todayBar.date + " 30 bars ago = " + comparisonBar.date)

      val roe = (((todayBar.close - comparisonBar.close) / comparisonBar.close) * 100).setScale(2, UP)

      (csv.getName, roe)
    }).toMap

    val json = symbolToLines.map {
      case (symbol, roe) => {
        println("symbol " + symbol  + " " + roe + "%")
        Map(
          "symbol" -> toJson(symbol),
          "roe" -> toJson(roe.toString)
        )
      }
    }.toList

    
    Ok(toJson(json))
  }


  def main(args: Array[String]) {
    IndustryController.findAll
  }
}


