package websocket;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class MyClientEndpoint extends Endpoint  
{
//	@Override
//	public void onClose(Session session, CloseReason closeReason) {
//		super.onClose(session, closeReason);
//	}
//
//	@Override
//	public void onError(Session session, Throwable throwable) {
//		super.onError(session, throwable);
//	}

	@Override
	public void onOpen(final Session session, EndpointConfig config) 
	{
		session.addMessageHandler(new MessageHandler.Whole<String>()
		{
			@Override
			public void onMessage(String message) {
				System.out.println("客户端收到消息:"+message);	
			}
			
		}); 
	}
}
