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
</dependency>  <!-- Ҫspring 5.0-->
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
		config.setMaxIdle(6);//������������, Ĭ��8��
		config.setMaxTotal(1000);//���������, Ĭ��8��
		config.setBlockWhenExhausted(false);//���Ӻľ�ʱ�Ƿ�����, false���쳣,ture����ֱ����ʱ, Ĭ��true 
		//û���ܿ������û��泬ʱ�������
		JedisConnectionFactory factory=new JedisConnectionFactory(config) ;//Ҫ��spring 5�汾,intellij Maven  ����OK
		
		
		
		factory.setUsePool(true);//��ʱ�Ĳ�������
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