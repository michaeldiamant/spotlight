package spotlight.engine.model

import java.util.Date

case class CorrelationResult(symbol1: String,  symbol2: String, data: List[(Date, Double)])

object CorrelationResult {
  
  def apply(request: CorrelationRequest, data: List[(Date,  Double)]): CorrelationResult =
    CorrelationResult(request.symbol1, request.symbol2, data)
}
