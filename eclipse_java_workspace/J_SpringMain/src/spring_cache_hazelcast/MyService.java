package spring_cache_hazelcast;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MyService 
{
	//longLive是HazelCast中的
	//@Cacheable(cacheNames="LongLive", key="#isbn.rawNumber")//使用isbn的一个属性当做key
	@Cacheable(value="LongLive", key="#isbn.rawNumber")//cacheNames和value是一样的，即别名@AliasFor("")
	public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)
	{
		System.out.println("invoke findBook" );
		if("key1".equals(isbn.getRawNumber()))
		{
			Book book1=new Book();
			book1.bookName="book1";
			return book1;
		}
		return null;
	}
	
	 
	
}
