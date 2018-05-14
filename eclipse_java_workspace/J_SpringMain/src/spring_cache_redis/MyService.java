package spring_cache_redis;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MyService 
{
	//Redis�о���key��ǰ׺::
	@Cacheable(cacheNames="MyRedis", key="#isbn.rawNumber")//ʹ��isbn��һ�����Ե���key
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
	public Book updateBook(ISBN isbn) //���ָ���DB
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
	
	@CacheEvict(cacheNames="MyRedis", allEntries=true)//ȫ������DB
	public void loadBooks(String bookpath)
	{
		
	}
	
}
