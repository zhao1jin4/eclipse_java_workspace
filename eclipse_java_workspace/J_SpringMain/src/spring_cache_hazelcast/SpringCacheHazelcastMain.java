package spring_cache_hazelcast;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;


 

public class SpringCacheHazelcastMain {

	public static void main(String[] args) { 
		anno( );
	} 
	public static void anno( ) { 
		ApplicationContext context = new AnnotationConfigApplicationContext(HazelCastConfig.class);
		MyService service = context.getBean(MyService.class);
		Book book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
		
		book1= service.findBook(()->"key1", false, false);
		System.out.println(book1.bookName);
	}
}
