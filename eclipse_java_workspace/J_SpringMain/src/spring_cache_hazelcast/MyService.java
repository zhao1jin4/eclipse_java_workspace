package spring_cache_hazelcast;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MyService 
{
	//longLive��HazelCast�е�
	//@Cacheable(cacheNames="LongLive", key="#isbn.rawNumber")//ʹ��isbn��һ�����Ե���key
	@Cacheable(value="LongLive", key="#isbn.rawNumber")//cacheNames��value��һ���ģ�������@AliasFor("")
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
