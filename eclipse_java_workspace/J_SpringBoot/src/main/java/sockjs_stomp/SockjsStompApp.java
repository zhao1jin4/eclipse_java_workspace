package sockjs_stomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SockjsStompApp {
	//SockJS  首先用webSocket,如果失败再偿试用其它协议
	//http://localhost:8081/J_SpringBoot/sockjs_stomp.html
		
	//   https://spring.io/guides/gs/messaging-stomp-websocket/ 的示例代码
    public static void main(String[] args) {
        SpringApplication.run(SockjsStompApp.class, args);
    }
}
