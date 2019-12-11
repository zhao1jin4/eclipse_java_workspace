package websocket.swing_client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketClient {

	public static void main(String[] args) throws Exception
	{   //spring webflux �� WebSocketClient
		//websocket ���ͻ���
		WebSocketClient	cc = new WebSocketClient( new URI( "ws://localhost:8080/J_JavaEE/webSocket")  )
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
		Thread.sleep(2*1000);//���������
		cc.send( "java_websocket �� ��Ϣ" );
		System.out.println( "�Ѿ������˷�����Ϣ");
		Thread.sleep(2*1000);//�ȷ��˻���Ϣ
		cc.close();
	}

}
