package sockjs_stomp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("topic");
        config.setApplicationDestinationPrefixes("app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("gs-guide-websocket")//对应于前端的 SockJS('gs-guide-websocket')
        .addInterceptors(new HttpSessionHandshakeInterceptor())
//        .setHandshakeHandler( )
        .withSockJS() 
        ; 
    }  
 
}
 
