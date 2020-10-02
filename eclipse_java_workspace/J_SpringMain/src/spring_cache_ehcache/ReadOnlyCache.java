package spring_cache_ehcache;

import org.springframework.cache.annotation.Cacheable;

public class ReadOnlyCache {
	//@声明式
	//@Cacheable(value = "DEFAULT_CACHE" ,key="#param")//对应于ehcache.xm中的配置,#引用参数变量
	@Cacheable(cacheNames = "DEFAULT_CACHE" ,key="#param")//cacheNames和value是一样的，即别名@AliasFor("")
	public String cacheTest(String param) throws Exception {
		System.out.println(" invoked cacheTest"+param  );
		return "[" + param + "] processed : " + param;
	}
}
