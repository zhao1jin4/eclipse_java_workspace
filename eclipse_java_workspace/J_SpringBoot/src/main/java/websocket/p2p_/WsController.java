package websocket.p2p_;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import websocket_.MyMessage;
import websocket_.MyResponse;

//书上的未测试呢
@Controller
@SendTo("/topic/getResponse")
public class WsController
{
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/chat")
	public void handleChat(Principal principal,String msg)
	{
		if(principal.getName().equals("zh"))
		{
			messagingTemplate.convertAndSendToUser("user1","/queue/notifications",principal.getName()+"-send:"+msg);
			
		}else
		{
			messagingTemplate.convertAndSendToUser("zh","/queue/notifications",principal.getName()+"-send:"+msg);
			
		}
	}
	public MyResponse say(MyMessage msg)
	{
		MyResponse resp= new MyResponse();
		resp.setRespMsg("hello "+msg.getName());
		return resp;
	}

}
