package redisson_springboot;

 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
 
public class RedissonSpringBoot {
	/**
	 * 默认配置 protected-mode yes 修改为 no ,CONFIG SET protected-mode no
 		redis-server  --requirepass redisPass --protected-mode no
 		
		#bind 默认127.0.0.1  是不能外网连接的，要修改为本机IP 或者注释它,或者修改为0.0.0.0
		
		因用netty，只能用JDK1.8
		
		
		 http://127.0.0.1:8081/springboot_redisson/saveMap
		 
		  http://127.0.0.1:8081/springboot_redisson/getMap
		   http://127.0.0.1:8081/springboot_redisson/redisTemplate
		  http://127.0.0.1:8081/springboot_redisson/redissonLock
		  
	 */
	public static void main(String[] args) {
		SpringApplication.run(RedissonSpringBoot.class, args);
	}
	 
}
