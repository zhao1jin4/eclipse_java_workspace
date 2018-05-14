package spring_cache_ehcache;

import org.springframework.cache.annotation.Cacheable;

public class ReadOnlyCache {
	//@声明式
	@Cacheable(value = "DEFAULT_CACHE" ,key="#param")//对应于ehcache.xm中的配置,#引用参数变量
	public String cacheTest(String param) throws Exception {
		System.out.println(" invoked cacheTest"+param  );
		return "[" + param + "] processed : " + param;
	}
}
