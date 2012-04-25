package spotlight.engine.model

sealed trait ComputationRequest

case class CorrelationRequest(symbol1:  String, symbol2: String, rangeInDays: Int)
