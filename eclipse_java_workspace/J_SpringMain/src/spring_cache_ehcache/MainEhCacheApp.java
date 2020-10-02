package spring_cache_ehcache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainEhCacheApp {

	public static void main(String[] args) throws Exception 
	{
		springAPI( ) ;
		//ehcacheAPI( ) ;
	}
	public static void springAPI( ) throws Exception 
	{ 
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_cache_ehcache/cache_anno.xml");
		ReadOnlyCache myCache = (ReadOnlyCache)context.getBean("myCache"); 
	    for(int i = 0; i < 3; i++) { 
	      for(int k = 0; k < 3; k++) { 
	        String cacheTestRest = myCache.cacheTest("param[" + k + "]"); 
	        System.out.println(cacheTestRest); 
	      } 
	    } 
	}
	
	public static void ehcacheAPI( ) throws Exception 
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_cache_ehcache/spring-ehcache.xml");
		//--·½Ê½ 2
		Ehcache ehCache=(Ehcache)context.getBean("ehcache2"); 
		ehCache.put(new Element("key", "obj"));
		Element element=ehCache.get("key");
		Object obj=element.getObjectValue();
		System.out.println(obj); 
		
		String tmpDir=System.getProperty("java.io.tmpdir");//C:\Users\zhaojin\AppData\Local\Temp\
		
		Set<String> allKeys = new HashSet<String>();
		allKeys.add("key");
		Map<Object, Element> localMap = ehCache.getAll(allKeys);
		System.out.println(localMap); 
	}

}
