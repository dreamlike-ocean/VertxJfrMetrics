package top.dreamlike

import io.vertx.core.spi.metrics.EventBusMetrics
import jdk.jfr.Category
import jdk.jfr.Event
import jdk.jfr.Name
import jdk.jfr.StackTrace

class JfrEventBusMetrics :EventBusMetrics<EventBusRegisteredEvent>{
  override fun handlerRegistered(address: String, repliedAddress: String?):EventBusRegisteredEvent{
    return EventBusRegisteredEvent(address, repliedAddress)
      .smartCommit{}
  }

  override fun handlerUnregistered(handler: EventBusRegisteredEvent) {
    handler.smartCommit {
      handler.isRegistered = false
    }
  }

  override fun messageSent(address: String, publish: Boolean, local: Boolean, remote: Boolean) {
    EventBusMessageEvent(address,true,local,remote)
      .smartCommit {  }
  }

  override fun messageReceived(address: String, publish: Boolean, local: Boolean, handlers: Int) {
    EventBusMessageEvent(address,false,local,false,handlers)
      .smartCommit {  }
  }


}
@Name("top.dreamlike.EventBusRegisteredEvent")
@Category("Vertx")
@StackTrace(false)
class EventBusRegisteredEvent(val address: String, val repliedAddress: String?,var isRegistered:Boolean = true):Event()

@Name("top.dreamlike.EventBusMessageEvent")
@Category("Vertx")
@StackTrace(false)
class EventBusMessageEvent(val address: String,val isSent:Boolean,val local: Boolean,val remote: Boolean,val handlers:Int = 0):Event()
