package client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import webflux.User;

public class RESTClient {
    public static void main(final String[] args) 
    {
        final User user = new User();
        user.setId("100");
        user.setName("Test");
        user.setEmail("test@example.org");
        //spring的reactive下的WebClient
        final WebClient client = WebClient.create("http://localhost:8080/user");
        final Mono<User> createdUser = client.post()
                .uri("/create")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()//请求服务端
                .flatMap(response -> response.bodyToMono(User.class));
        System.out.println(createdUser.block());
        
    }
}