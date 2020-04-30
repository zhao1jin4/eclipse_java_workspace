package cache_guava;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCacheMain {

	public static void main(String[] args) {
		 LoadingCache<String,Object> dictCache = CacheBuilder.newBuilder()
				.maximumSize( 10000) 
				.expireAfterWrite( 7200 , TimeUnit.SECONDS)
				.concurrencyLevel( 5 )
				.build(new CacheLoader<String,Object>() {
					@Override
					public String load(String key) throws Exception {
						return null;
					}
				});
		 dictCache.put("key", "value");
		 Object o= dictCache.getIfPresent("key");
		 System.out.println(o);
		
		 dictCache.invalidate("key");
		 
		 o= dictCache.getIfPresent("key");
		 System.out.println(o);
				
	}

}
