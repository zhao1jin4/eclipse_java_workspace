package rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQResetController {
	 
	@Autowired
	private Send send;
 
 
	@RequestMapping("rabbitMQ")
	public String sayHello() {
		send.sendMsg("HELLO ");
		return "rabbitMQ SEND HELLO";
	}
	
	 
	
	
}
