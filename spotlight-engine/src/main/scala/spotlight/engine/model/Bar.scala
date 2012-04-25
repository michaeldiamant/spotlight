package spotlight.engine.model

import java.util.Date

case class Bar(open: BigDecimal, high: BigDecimal, low: BigDecimal,
               close: BigDecimal, volume: Long, date: Date)