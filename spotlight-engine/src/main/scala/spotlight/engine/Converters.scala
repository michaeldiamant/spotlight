package spotlight.engine

import spotlight.engine.model.Bar
import java.text.SimpleDateFormat

class YahooDailyConverter {

  private val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def convert(contents: Array[String]): Bar =
    Bar(
      date = sdf.parse(contents(1)),
      open = BigDecimal(contents(2)),
      high = BigDecimal(contents(3)),
      low = BigDecimal(contents(4)),
      close = BigDecimal(contents(5)),
      volume = contents(6).toLong
    )
}