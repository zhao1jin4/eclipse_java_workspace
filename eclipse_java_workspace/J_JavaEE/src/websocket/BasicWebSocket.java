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
import javax.websocket.server.ServerEndpointConfig;


@ServerEndpoint("/webSocket") //Tomcat 8 才支持
public class BasicWebSocket {

	public void init()//编程式部署，放入其它的启动初始化方法中
	{
		ServerEndpointConfig.Builder.create(MyServerEchoEndpoint.class, "/echo").build();
	}
	
	
    private static Set<Session> peers = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session session) {
    	System.out.println("连接打开");
        peers.add(session);
    }

    @OnClose
    public void onClose(Session session) {
    	System.out.println("连接关闭");
        peers.remove(session);
    }

    @OnMessage
    public void shapeCreated(String message, Session client) throws IOException, EncodeException {
        System.out.println("收到消息:"+message);
        
    	for (Session otherSession : peers) {
            //if (!otherSession.equals(client)) {
                otherSession.getBasicRemote().sendText("Server端消息");
             //}
        }
    }
}
