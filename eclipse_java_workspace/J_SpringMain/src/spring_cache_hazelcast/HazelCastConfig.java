package spring_cache_hazelcast;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;


@Configuration
@EnableCaching
public class HazelCastConfig {
	@Bean
	public HazelcastInstance hazelcastInstance() {
		Config config=new Config();
		HazelcastInstance h=Hazelcast.newHazelcastInstance(config); 
		return h;
	}
	@Bean //hazelcast-spring-4.0.1.jar
	public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
		MapConfig longLive=new MapConfig("LongLive").setBackupCount(0);
		longLive.setTimeToLiveSeconds(60*10);//设置过期时间
		longLive.setReadBackupData(false);
		hazelcastInstance.getConfig().addMapConfig(longLive);
		return new HazelcastCacheManager(hazelcastInstance);
	}
	@Bean
	public MyService myService()
	{
		return new MyService();
	}
}
