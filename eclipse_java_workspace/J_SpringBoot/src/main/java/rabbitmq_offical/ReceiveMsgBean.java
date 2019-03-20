package rabbitmq_offical;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMsgBean {

	// 报 Login was refused using authentication mechanism PLAIN 
	//是queueName 已经存在也是一样的错
	@RabbitListener(queues = "someQueue")//, containerFactory="myFactory"  //SimpleRabbitListenerContainerFactory
	public void processMessage(String content) {
		System.out.println(" spring bootRabbitMQ  收到:"+content);
	}

}