package websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/myMsgWebSocket",
		decoders = {MyMessageDecoder.class}, //implements Decoder.Text<T> 请求时如@OnMessage方法的第一个参数是自定义类型,把String->T
		encoders = { MyMessageEncoder.class  }) //implements Encoder.Text<T>   T->String
public class MyMessageWebSocket  
{ 
   private static Set<Session> allSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
   @OnOpen
    public void onOpen(Session session) {
    	allSessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        allSessions.remove(session);
    }

    @OnMessage
    public void onMessage(MyMessage message, Session client)//如第一个参数是自定义类型会使用decoders和encoders,如String就不用
				throws IOException, EncodeException 
	{
    	Set<Session> opendSessions=client.getOpenSessions();//所有打开的
    	System.out.println("all connected is  :"+opendSessions);
    	
        for (Session otherSession : allSessions) 
		{
            if (otherSession.equals(client)) 
			{
               //otherSession.getBasicRemote().sendText(message);//发String消息到客户端
            	System.out.println("服务端收到:"+message);
            	message.setData("__server_add_"+message.getData());
            	otherSession.getBasicRemote().sendObject(message);//要Encoder ,T->String
            	client.getUserProperties().put("key","my-obj");//是个Map
            	Object stored=client.getUserProperties().get("key");
            	System.out.println("store key is :"+stored);
            }
        }
    }
   
}