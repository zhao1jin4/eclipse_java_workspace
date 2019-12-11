package webflux.websocket;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

@Component
//webSocket双向的
public class EchoHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(final WebSocketSession session) {
        return session.send(  //参数类型为 Publisher<WebSocketMessage> 
                session.receive()//返回Mono<WebSocketMessage>
                        .map(msg -> session.textMessage("ECHO -> " + msg.getPayloadAsText())));
    }
}

