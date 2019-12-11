package client;

import java.net.URI;
import java.time.Duration;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Flux;

public class WSClient {
    public static void main(final String[] args) {
    	//webSocket客户端
        final WebSocketClient client = new ReactorNettyWebSocketClient();
        //execute函数与服务端连接
        client.execute(URI.create("ws://localhost:8080/echo"),
		        		session ->
		        			session.send(Flux.just(session.textMessage("Hello")))
		                        .thenMany(
		                        		//receive 方法来等待服务器端的响应并输出。take(1)的作用是客户端只获取服务器端发送的第一条消息。
		                        		session.receive().take(1)
		                        		.map(WebSocketMessage::getPayloadAsText)//getPayloadAsText函数没有参数,可以不接收？？？
		                        		)
		                        .doOnNext(System.out::println)
		                        .then()
		               ).block(Duration.ofMillis(5000));
    }
}