package caffeine;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class MainCaffeine {
	public static void main(String[] args) 
	{
		LoadingCache<String, Object> graphs = Caffeine.newBuilder()
				.maximumSize(10_000)
				.expireAfterWrite(5, TimeUnit.MINUTES)
				.refreshAfterWrite(1, TimeUnit.MINUTES)
				.build(new  CacheLoader<String, Object>() {
					@Override
					public Object load(String arg0) throws Exception {
						
						return null;
					}
				});
				//.build(key -> createExpensiveGraph(key));
	}
}
