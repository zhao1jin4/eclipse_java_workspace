package spring_cache_redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig {

/* 
  <dependency>
	<groupId>org.springframework.data</groupId>
	<artifactId>spring-data-redis</artifactId>
	<version>2.0.5.RELEASE</version>
</dependency>  <!-- 要spring 5.0-->
 */
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.create(connectionFactory);
	}
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory( ) 
	{
		/*
		JedisPoolConfig config =new JedisPoolConfig();
		config.setMaxIdle(6);//最大空闲连接数, 默认8个
		config.setMaxTotal(1000);//最大连接数, 默认8个
		config.setBlockWhenExhausted(false);//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true 
		//没有总开关设置缓存超时，最大数
		JedisConnectionFactory factory=new JedisConnectionFactory(config) ;//要求spring 5版本,intellij Maven  测试OK
		
		
		
		factory.setUsePool(true);//过时的参数设置
		factory.setTimeout(3000);
		factory.setHostName("120.55.90.245");
		factory.setPassword("test2016");
		factory.setPort(6380);
		return factory;
		*/
		
		
		RedisStandaloneConfiguration standConfig=new RedisStandaloneConfiguration();
		standConfig.setHostName("120.55.90.245");
		standConfig.setPort(6380);
		standConfig.setPassword(RedisPassword.of("test2016"));
		standConfig.setDatabase(0);
		JedisConnectionFactory factory2=new JedisConnectionFactory(standConfig) ;
		return factory2;
	}
  

	@Bean
    public MyService newService()
    {
		return new MyService();
    }
}