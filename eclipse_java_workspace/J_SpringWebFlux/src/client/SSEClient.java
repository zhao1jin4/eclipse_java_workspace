package client;

import java.util.Objects;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

public class SSEClient {
    public static void main(final String[] args) {
    	/*
    	 java.lang.UnsatisfiedLinkError: no netty_transport_native_epoll_x86_64 in java.library.path
    	 spring boot版本pom 中已经自带下面的，还是报错，但不影响使用
    	<dependency>
	      <groupId>io.netty</groupId>
	      <artifactId>netty-transport-native-epoll</artifactId>
	      <!--  <version>4.1.43.Final</version>  --> 
	     <classifier>linux-x86_64</classifier>
	    </dependency> 
    	 */
        final WebClient client = WebClient.create();
        client.get()
                .uri("http://localhost:8080/sse/randomNumbers")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()//返回Mono,后全是reactor的方法
                //使用 flatMapMany 把 Mono<ClientResponse>转换成一个 Flux<ServerSentEvent>对象
                .flatMapMany(response -> 
                				response.body(
                						BodyExtractors.toFlux( 
                								//表未了响应消息流中的内容是 ServerSentEvent 对象
                								new ParameterizedTypeReference<ServerSentEvent<String>>() {}
                								)
                			 	)
                			)
                .filter(sse -> Objects.nonNull(sse.data()))
                .map(ServerSentEvent::data)//data函数没有参数,可以不接收？？？
                .buffer(10)//来获取前 10 条消息并输出
                .doOnNext(System.out::println)
                .blockFirst();
    }
}