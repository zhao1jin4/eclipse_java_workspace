package cache_ehcache2x;


import java.net.URL;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class MainEhCache {

	public static void ehCacheSelf() 
	{
		CacheManager manager1 = CacheManager.newInstance("src/ehcache/ehcache.xml");
		Cache cache1 = manager1.getCache("sampleCache1");
		Element element1 = new Element("key1", "value1");
		cache1.put(element1);
		
		//---
		URL url = MainEhCache.class.getResource("/ehcache/ehcache.xml");
		CacheManager manager = CacheManager.newInstance(url);
		//--------
		Cache cache = manager.getCache("sampleCache1");
		Element element = new Element("key1", "value1");
		cache.put(element);//新建和更新
		
		
		Element valElement = cache.get("key1");
		Object value = valElement.getObjectValue();
		
		int elementsInMemory = cache.getSize();//已经使用的内存大小
		cache.remove("key1");
		
		//-----
		manager.shutdown();
		CacheManager.getInstance().shutdown();
	}
	public static void ehCacheSpring()
	{
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("ehcache/applicationContext-cache.xml");
		Ehcache  ehcache=ctx.getBean("ehCache",Ehcache.class);
		Element element = new Element("key1", "value1");
		ehcache.put(element);
		Element valElement = ehcache.get("key1");
		Object value = valElement.getObjectValue();
	}
	//Ehcache 2.9
	public static void main(String[] args) 
	{
		//ehCacheSelf();
		ehCacheSpring();
	}

}
