package spring_cache_guava_bak;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class AppConfig {
	/*
	//spring 5.x 没有了	org.springframework.cache.guava包
	org.springframework.cache.guava.GuavaCacheManager x;
	private GuavaCache buildHotelPositionCache() {
	        return new GuavaCache("MyGuava",
	                CacheBuilder.newBuilder()
	                        .recordStats()
	                        .maximumSize(1000)
	                        .expireAfterWrite(1, TimeUnit.DAYS)
	                        .build());
	}

	@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        List<GuavaCache> list = new ArrayList<>();
        list.add(buildHotelPositionCache());
        manager.setCaches(  list );
        return manager;
    }
	@Bean
    public MyService newService()
    {
		return new MyService();
    }
    */
}