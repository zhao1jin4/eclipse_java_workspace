package redis_cluster;

 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching//Redis cluster不用这个 
 
public class RedisClusterSpringBoot {
 
	//配置cluster-enabled yes及很多
	//CLUSTER info 命令显示 cluster_state:fail 是不行的
	
	public static void main(String[] args) {
		SpringApplication.run(RedisClusterSpringBoot.class, args);
	}
	 
}
