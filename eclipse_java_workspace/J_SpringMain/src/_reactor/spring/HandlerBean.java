package _reactor.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.context.annotation.ReplyTo;
import reactor.spring.context.annotation.Selector;

@Component
public class HandlerBean { 
	@Autowired  
    @Qualifier("rootReactor")  
    private Reactor reactor;  
  
 // @Selector(value="test.topic", reactor="@rootReactor")
  @Selector("test.topic")
  @ReplyTo("reply.topic")
  public void handleTestTopic(Event<String> evt) {
    // handle the event
	  System.out.println("收到事件数据:"+evt.getData());
  }

}