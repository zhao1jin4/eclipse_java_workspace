package webflux;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;


// https://www.ibm.com/developerworks/cn/java/spring5-webflux-reactive/index.html
//http://127.0.0.1:8080/hello_world
/*
POST  http://127.0.0.1:8080/user/create  
{
	"id":1,
	"name":"lisi"
}
 
  http://127.0.0.1:8080/user/2     如没数据就返回指定错误码
  http://127.0.0.1:8080/user/list
  
curl   http://localhost:8080/sse/randomNumbers 

 websocket 使用Java-WebSocket库测试
 springboot jar项目不能建webapp目录，只能模板,webflux 静态html放static不行，放哪？？
 
 --函数式
 http://localhost:8080/calculator?operator=add&v1=4&v2=5
 http://localhost:8080/calculator?operator=xxx&v1=4&v2=5
 http://localhost:8080/calculator?operatorxxx=xxx&v1=4&v2=5
 * 

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
*/