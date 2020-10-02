package springdata_redis_lettuce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class SpringLettuce {
	
	 
    public static void main(String[] args) {
//    	lettuceAPI();
    	springDataLettuce();
    }
    
    @Bean
	 public LettuceConnectionFactory redisConnectionFactory()
	 {
	    return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
	 }
    public static void springDataLettuce() {
    	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                 "springdata_redis_lettuce/spring_lettuce_client.xml");
    	 LettuceConnectionFactory factory = context.getBean(LettuceConnectionFactory.class);
    	 RedisConnection conn=factory.getConnection();
    	 conn.set("key".getBytes(), "val".getBytes());
    	 conn.close();
    }
    public static void lettuceAPI() {
    	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                 "springdata_redis_lettuce/spring_lettuce_client.xml");

         RedisClient client = context.getBean(RedisClient.class);

         StatefulRedisConnection<String, String> connection = client.connect();

         RedisCommands<String, String> sync = connection.sync();
         System.out.println("PING: " + sync.ping());
         connection.close();
  
         context.close();
    }
}