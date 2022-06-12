package top.dreamlike

import io.vertx.core.VertxOptions
import io.vertx.core.spi.VertxMetricsFactory
import io.vertx.core.spi.metrics.VertxMetrics
import jdk.jfr.Event

class JfrVertxMetricsFactory: VertxMetricsFactory {
  override fun metrics(options: VertxOptions): VertxMetrics {
    return JfrVertxMetrics()
  }

}
inline fun <T:Event> T.smartCommit(init:T.()->Unit):T{
  if (shouldCommit()){
    init(this)
    commit()
  }
  return this
}
