package spring_cache_ehcache;

import org.springframework.cache.annotation.Cacheable;

public class ReadOnlyCache {
	//@����ʽ
	//@Cacheable(value = "DEFAULT_CACHE" ,key="#param")//��Ӧ��ehcache.xm�е�����,#���ò�������
	@Cacheable(cacheNames = "DEFAULT_CACHE" ,key="#param")//cacheNames��value��һ���ģ�������@AliasFor("")
	public String cacheTest(String param) throws Exception {
		System.out.println(" invoked cacheTest"+param  );
		return "[" + param + "] processed : " + param;
	}
}
