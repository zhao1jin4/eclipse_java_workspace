package springweb_stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
@Configuration
@EnableWebMvc
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
 
 
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    System.out.println("注册");
    registry.addEndpoint("/hello").withSockJS(); // 注册端点，和普通服务端的/log一样的
    // withSockJS()表示支持socktJS访问，在浏览器中使用
  }
 
 
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    System.out.println("启动");
    registry.enableSimpleBroker("/topic"); // 
    registry.setApplicationDestinationPrefixes("/app"); // 格式前缀
  }
 
}