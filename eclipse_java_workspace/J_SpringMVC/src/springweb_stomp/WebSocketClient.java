package springweb_stomp;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WebSocketClient {

	public static void main(String[] args) {
	    try {
	      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
	      String uri = "ws://localhost:8081/J_SpringMVC/hello/hello/websocket";
	      Session session = container.connectToServer(Client.class, new URI(uri));
	      char lf = 10; // ����ǻ���
	      char nl = 0; // �������Ϣ��β�ı�ǣ�һ��Ҫ
	      StringBuilder sb = new StringBuilder();
	      sb.append("SEND").append(lf); // ������������
	      sb.append("destination:/app/hello").append(lf); // �������Դ
	      sb.append("content-length:14").append(lf).append(lf); // ��Ϣ��ĳ���
	      sb.append("{\"name\":\"123\"}").append(nl); // ��Ϣ��
	 
	      session.getBasicRemote().sendText(sb.toString()); // ������Ϣ
	      Thread.sleep(50000); // �ȴ�һС��
	      session.close(); // �ر�����
	 
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
