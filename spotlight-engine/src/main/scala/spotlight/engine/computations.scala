package spotlight.engine

import math.statistics.Statistics
import io.Source
import model.{CorrelationResult, CorrelationRequest}

class Computations {

   def correlation(request: CorrelationRequest): CorrelationResult = {
     val spy = Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("data/" + request.symbol1.toLowerCase + ".csv"))
     val uga = Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("data/" + request.symbol2.toLowerCase + ".csv"))

     val converter = new YahooDailyConverter().convert(_)
     def bars(source: Source) =
       source.getLines().toList.tail.map(l => converter(l.split(",")))

     try {
       val stats = new Statistics {}

       val x = bars(spy).takeRight(60).iterator.sliding(request.rangeInDays).toList.reverse
       val y = bars(uga).takeRight(60).iterator.sliding(request.rangeInDays).toList.reverse

       val corrs = x.zip(y).map {
         case (spyWindow, ugaWindow) => {
           (
             spyWindow.last.date,
             stats.correlation(spyWindow.map(_.close), ugaWindow.map(_.close))
             )
         }
       }

       CorrelationResult(request, corrs)
     } finally {
       spy.close()
       uga.close()
     }
   }
}
