package top.dreamlike

import io.vertx.core.Vertx
import io.vertx.core.metrics.MetricsOptions
import io.vertx.kotlin.core.vertxOptionsOf



val event = VertxInitEvent()
fun main() {
  val metricsOptions = MetricsOptions().setEnabled(true)
    .setFactory(JfrVertxMetricsFactory())
  event.begin()
  val vertx = Vertx.vertx(vertxOptionsOf(metricsOptions = metricsOptions))
 vertx.eventBus()
   .localConsumer<String>("local")
   .handler {
   }
  vertx.eventBus()
    .localConsumer<String>("local")
    .handler {
    }

  vertx.setTimer(1000){
    vertx.eventBus().publish("local","")

  }

}
