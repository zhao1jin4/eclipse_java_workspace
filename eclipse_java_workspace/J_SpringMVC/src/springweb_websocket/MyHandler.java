package springweb_websocket;
import java.io.IOException;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
    	
		System.out.println("收到的消息"+message.getPayload());
    	try {
    		session.sendMessage( new TextMessage("Spring websocket 的消息"));
    		
    		//才下方式不行
//    		session.sendMessage(new WebSocketMessage<String>() {
//				String str="Spring websocket 的消息";
//				@Override
//				public String getPayload() {
//					return str;
//				}
//				@Override
//				public int getPayloadLength() {
//					return str.getBytes().length;
//				}
//				@Override
//				public boolean isLast() {
//					return true;
//				}
//			});
    		
    		 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}