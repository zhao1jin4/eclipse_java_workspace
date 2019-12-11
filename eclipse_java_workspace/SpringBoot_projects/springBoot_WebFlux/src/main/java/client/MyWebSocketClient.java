package client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketClient {

	public static void main(String[] args) throws Exception
	{
		//websocket 做客户端
		WebSocketClient	cc = new WebSocketClient( new URI( "ws://localhost:8080/echo")  )
				{
					@Override
					public void onMessage( String message ) {
						System.out.println( "got: " + message + "\n" );
					}
					@Override
					public void onOpen( ServerHandshake handshake ) {
						System.out.println( "You are connected to ChatServer: " + getURI() + "\n" );
					}
					@Override
					public void onClose( int code, String reason, boolean remote ) {
						System.out.println( "You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason + "\n" );
					}
					@Override
					public void onError( Exception ex ) {
						System.out.println( "Exception occured ...\n" + ex + "\n" );
						ex.printStackTrace();
					}
				};
				cc.connect();
				Thread.sleep(2*1000);//等连接完成
				cc.send( "java_websocket 端 消息" );
				System.out.println( "已经向服务端发送消息");
				Thread.sleep(2*1000);//等服端回消息
				cc.close();

	}

}
