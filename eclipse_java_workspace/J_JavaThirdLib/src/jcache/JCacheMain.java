package jcache;

 

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
 

public class JCacheMain {

	public static void main(String[] args) {
		 
		jcache();
	}
	 static void jcache()
	{
		 //JCache (JSR-107) classpath�в��ж��ʵ�֣�ȥ redisson.jar
		CachingProvider provider = Caching.getCachingProvider();  
		
		//---CacheManager ��ʽһ
		DefaultConfiguration defConfiguration = new DefaultConfiguration(provider.getDefaultClassLoader(),
				  new DefaultPersistenceConfiguration(new File("/tmp/ehcache_dist/"))); 
		javax.cache.CacheManager cacheManager =provider.getCacheManager(provider.getDefaultURI(),provider.getDefaultClassLoader());  
		 
		//---CacheManager ��ʽ ��
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
