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
	
	//�����б���������  , �ô���TransactionProxyFactoryBean, �������쳣Ҳ���ύ����????
	public void addCategory(Category cate1,Category cate2) throws Exception
	{
		int effectRow=insert.save(cate1);
		if(1+1 == 2)
		{ 
		   throw new Exception("�д�����");  
		}
		insert.save(cate2);
	}
	//�����б���������
	public Category findById(int id )
	{
		List l=query.execute(1);
		System.out.println(l);
		
		return (Category)DataAccessUtils.uniqueResult(l);
	}
}
