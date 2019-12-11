package test;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import webflux.User;

//报 no netty_transport_native_epoll_x86_64 in java.library.path
//不影响运行
public class UserControllerTest {
	//单元测试,spring-test中带webflux
    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    @Test
    public void testCreateUser() throws Exception {
        final User user = new User();
        user.setId("200");
        user.setName("Test");
        user.setEmail("test@example.org");
        client.post().uri("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("name").isEqualTo("Test");
    }
}