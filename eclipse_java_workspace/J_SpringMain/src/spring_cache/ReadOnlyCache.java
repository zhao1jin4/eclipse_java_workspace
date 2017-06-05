package spring_cache;

import org.springframework.cache.annotation.Cacheable;

public class ReadOnlyCache {
	@Cacheable(value = "DEFAULT_CACHE" ,key="#param")//对应于ehcache.xm中的配置,#引用参数变量
	public String cacheTest(String param) throws Exception {
		Thread.sleep(1000 * 1);
		return "[" + param + "] processed : " + param;
	}
}
