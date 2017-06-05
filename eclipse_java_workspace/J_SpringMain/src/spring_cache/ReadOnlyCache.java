package spring_cache;

import org.springframework.cache.annotation.Cacheable;

public class ReadOnlyCache {
	@Cacheable(value = "DEFAULT_CACHE" ,key="#param")//��Ӧ��ehcache.xm�е�����,#���ò�������
	public String cacheTest(String param) throws Exception {
		Thread.sleep(1000 * 1);
		return "[" + param + "] processed : " + param;
	}
}
