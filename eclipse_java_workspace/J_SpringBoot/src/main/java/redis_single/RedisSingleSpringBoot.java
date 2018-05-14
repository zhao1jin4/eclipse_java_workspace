package redis_single;

 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching//Redis 
 
public class RedisSingleSpringBoot {
	
 //测试OK
//redis 4  
//	默认配置 protected-mode yes 修改为 no
//	bind 默认127.0.0.1  是不能外网连接的，要修改为本机IP或者 注释它 
	//注释 spring.redis.cluster.nodes
	public static void main(String[] args) {
		SpringApplication.run(RedisSingleSpringBoot.class, args);
	}
	 
}
