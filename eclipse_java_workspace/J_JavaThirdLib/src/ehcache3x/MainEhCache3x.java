package ehcache3x;

 

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
 

public class MainEhCache3x {

	public static void main(String[] args) {
		//echcache3x();
		jcache();
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
	
	 static void jcache()
	{
		 //JCache (JSR-107) classpath中不有多个实现，去 redisson.jar
		CachingProvider provider = Caching.getCachingProvider();  
		
		//---CacheManager 方式一
		DefaultConfiguration defConfiguration = new DefaultConfiguration(provider.getDefaultClassLoader(),
				  new DefaultPersistenceConfiguration(new File("/tmp/ehcache_dist/"))); 
		javax.cache.CacheManager cacheManager =provider.getCacheManager(provider.getDefaultURI(),provider.getDefaultClassLoader());  
		 
		//---CacheManager 方式 二
		//javax.cache.CacheManager cacheManager = provider.getCacheManager();  
		
		MutableConfiguration<Long, String> configuration =
		    new MutableConfiguration<Long, String>()  
		        .setTypes(Long.class, String.class)   
		        .setStoreByValue(false)   
		        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(javax.cache.expiry.Duration.ONE_MINUTE));  
		javax.cache.Cache<Long, String> cache = cacheManager.createCache("jCache", configuration); 
		cache.put(1L, "one"); 
		String value = cache.get(1L); 
		
	}
}
