package top.dreamlike

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.net.NetServerOptions
import io.vertx.core.net.SocketAddress
import io.vertx.core.spi.metrics.*
import jdk.jfr.Category
import jdk.jfr.Event
import jdk.jfr.Name
import jdk.jfr.StackTrace

class JfrVertxMetrics: VertxMetrics {
  override fun vertxCreated(vertx: Vertx) {
    event.end()
    event.smartCommit {
      isClustered = vertx.isClustered
      isNativeTransportEnabled = vertx.isNativeTransportEnabled
      vertx.nettyEventLoopGroup().forEach { eventLoopCount ++ }
    }

  }

  override fun createEventBusMetrics(): EventBusMetrics<*> {
    return JfrEventBusMetrics()
  }

  override fun createHttpServerMetrics(
    options: HttpServerOptions,
    localAddress: SocketAddress
  ): HttpServerMetrics<*, *, *> {
    return super.createHttpServerMetrics(options, localAddress)
  }

  override fun createNetServerMetrics(options: NetServerOptions?, localAddress: SocketAddress?): TCPMetrics<*> {
    return super.createNetServerMetrics(options, localAddress)
  }
}
@Name("top.dreamlike.VertxInit")
@Category("Vertx")
@StackTrace(false)
class VertxInitEvent: Event() {
  public var isClustered:Boolean = false

  public var isNativeTransportEnabled:Boolean = false

  public var eventLoopCount = 0
}
