package spring_session;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/*
// 这个文件   顺序优于 application.properties
spring.redis.host=192.168.56.101
spring.redis.port=6379
spring.redis.password=redisPass

 */ 
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=600)
public class SessionConfig {
	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration standConfig=new RedisStandaloneConfiguration();
		standConfig.setHostName("192.168.56.101");
		standConfig.setPort(6379);
		standConfig.setPassword(RedisPassword.of("redisPass"));
		standConfig.setDatabase(0);
		JedisConnectionFactory factory2=new JedisConnectionFactory(standConfig) ;
		return factory2;
	}
}