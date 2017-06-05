package spring_db_transaction;

import java.util.List;
 

import org.springframework.dao.support.DataAccessUtils;


public class CategoryDao
{

	private InsertCategory insert;
	private QueryCategory query;

	public void setInsert(InsertCategory insert)
	{
		this.insert = insert;
	}
	public void setQuery(QueryCategory query)
	{
		this.query = query;
	}
	
	//配置中被加事务了  , 用代理TransactionProxyFactoryBean, 如有抛异常也会提交事务????
	public void addCategory(Category cate1,Category cate2) throws Exception
	{
		int effectRow=insert.save(cate1);
		if(1+1 == 2)
		{ 
		   throw new Exception("有错误发生");  
		}
		insert.save(cate2);
	}
	//配置中被加事务了
	public Category findById(int id )
	{
		List l=query.execute(1);
		System.out.println(l);
		
		return (Category)DataAccessUtils.uniqueResult(l);
	}
}
