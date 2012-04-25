package spotlight.engine.math.statistics

import org.apache.commons.math.stat.correlation.PearsonsCorrelation
import Numeric.Implicits._

trait Statistics {

  def correlation[T: Numeric](x: Iterable[T], y: Iterable[T]) =
    new PearsonsCorrelation().correlation(x.map(_.toDouble()).toArray, y.map(_.toDouble()).toArray)
}
