package spring_cache_redis;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MyService 
{
	//Redis中就是key的前缀::
	@Cacheable(cacheNames="MyRedis", key="#isbn.rawNumber")//使用isbn的一个属性当做key
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
	
	@CachePut(cacheNames="MyRedis", key="#isbn.rawNumber")
	public Book updateBook(ISBN isbn) //部分更新DB
	{
		System.out.println("invoke updateBook" );
		if("key1".equals(isbn.getRawNumber()))
		{
			Book book1=new Book();
			book1.bookName="book11";
			return book1;
		}
		return null;
	}
	
	@CacheEvict(cacheNames="MyRedis", allEntries=true)//全部更新DB
	public void loadBooks(String bookpath)
	{
		
	}
	
}
