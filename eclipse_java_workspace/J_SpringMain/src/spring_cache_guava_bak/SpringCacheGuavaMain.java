package spring_cache_guava_bak;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring_cache_ehcache.ReadOnlyCache;

 

public class SpringCacheGuavaMain {

	public static void main(String[] args) {
		//xml( );
		anno( );
	}
	public static void xml( ) { 
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_cache_guava/cache_guava.xml");
		MyService service =  context.getBean("myService",MyService.class); 
		Book book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
		book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
	}
	public static void anno( ) { 
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		MyService service = context.getBean(MyService.class);
		Book book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
		
		book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
		
		
		
		service.loadBooks(null);
		book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
		
		
		service.updateBook(()->"key1");
		book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
		
	}
}
