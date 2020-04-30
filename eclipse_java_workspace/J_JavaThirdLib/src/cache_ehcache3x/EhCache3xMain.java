package cache_ehcache3x;

 

import java.io.File;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.spi.CachingProvider;
 
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.impl.config.persistence.DefaultPersistenceConfiguration;
 

public class EhCache3xMain {

	public static void main(String[] args) {
		echcache3x();
	}
	
	//org.terracotta.statistics.StatisticsManager s;
	static void echcache3x()
	{
		//classpath 中不能有ehcache 2.9
		 org.ehcache.CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				          .withCache("preConfigured",
				               CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
				                                              ResourcePoolsBuilder.heap(100))
				               .build())
				          .build(true);

		 org.ehcache.Cache<Long, String> preConfigured
				          = cacheManager.getCache("preConfigured", Long.class, String.class);

		 org.ehcache.Cache<Long, String> myCache = cacheManager.createCache("myCache",
				          CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
				                                        ResourcePoolsBuilder.heap(100)).build());
				      myCache.put(1L, "da one!");
				      String value = myCache.get(1L);

				      cacheManager.close();
	}
	
	 
}
