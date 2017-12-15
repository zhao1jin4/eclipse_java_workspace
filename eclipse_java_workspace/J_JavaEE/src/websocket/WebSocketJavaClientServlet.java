package websocket;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Extension;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@WebServlet("/webSocketJavaClient")
public class WebSocketJavaClientServlet extends HttpServlet 
{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
				WebSocketContainer container = ContainerProvider.getWebSocketContainer();//实现 META-INF/services/javax.websocket.ContainerProvider
				
				Session session=container.connectToServer(MyClientEndpoint.class,  //收到服务端消息的回调类
							ClientEndpointConfig.Builder.create().build(),
							new URI("ws://localhost:8080/J_JavaEE/myMsgWebSocket"));
				session.getBasicRemote().sendText("obj_type123:data_123客户端发送的Test消息");
				//session.getBasicRemote().sendObject(new MyMessage("obj_type","123_data"));//报错 因使用的 Decoder.Text 
				Thread.sleep(3*1000);
				session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
