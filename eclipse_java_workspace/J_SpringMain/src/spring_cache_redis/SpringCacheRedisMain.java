package spring_cache_redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;


 

public class SpringCacheRedisMain {

	public static void main(String[] args) {
//		org.springframework.util.Assert.isTrue ( );
		anno( );
	}
	 
	public static void anno( ) { 
		ApplicationContext context = new AnnotationConfigApplicationContext(RedisConfig.class);
		
		RedisConnectionFactory redisConnectionFactory = context.getBean(RedisConnectionFactory.class);
		RedisConnection conn=redisConnectionFactory.getConnection();
		conn.del("MyRedis::key1".getBytes());
		conn.close();
		
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
