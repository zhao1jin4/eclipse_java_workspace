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
    System.out.println("ע��");
    registry.addEndpoint("/hello").withSockJS(); // ע��˵㣬����ͨ����˵�/logһ����
    // withSockJS()��ʾ֧��socktJS���ʣ����������ʹ��
  }
 
 
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    System.out.println("����");
    registry.enableSimpleBroker("/topic"); // 
    registry.setApplicationDestinationPrefixes("/app"); // ��ʽǰ׺
  }
 
}