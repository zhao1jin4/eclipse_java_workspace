package spring_cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainCacheApp {

	public static void main(String[] args) throws Exception {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_cache/cache_anno.xml");
		ReadOnlyCache myCache = (ReadOnlyCache)context.getBean("myCache"); 
	    for(int i = 0; i < 3; i++) { 
	      for(int k = 0; k < 3; k++) { 
	        String cacheTestRest = myCache.cacheTest("param[" + k + "]"); 
	        System.out.println(cacheTestRest); 
	      } 
	    } 
	    //--·½Ê½ 2
		Ehcache ehcache=(Ehcache)context.getBean("ehcache2"); 
		ehcache.put(new Element("key", "obj"));
		Element element=ehcache.get("key");
		Object obj=element.getObjectValue();
		String tmpDir=System.getProperty("java.io.tmpdir");//C:\Users\zhaojin\AppData\Local\Temp\
//		 Set<String> allKeys = new HashSet<String>();
//		 Map<Object, Element> localMap = ehCache.getAll(allKeys);
		
	}

}
