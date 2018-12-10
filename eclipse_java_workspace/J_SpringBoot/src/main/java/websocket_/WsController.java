package websocket_;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//书上的未测试呢
@Controller
@SendTo("/topic/getResponse")
public class WsController
{
	public MyResponse say(MyMessage msg)
	{
		MyResponse resp= new MyResponse();
		resp.setRespMsg("hello "+msg.getName());
		return resp;
	}

}
