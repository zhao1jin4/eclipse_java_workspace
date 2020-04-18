package test_mockito;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//@Service
public class MyServiceBean 
{
	@Autowired
	private MyDao dao;

	public void setDao(MyDao dao) {
		this.dao = dao;
	}
	
	public long insertData(List<Product> data)
	{
		System.out.println("---insertData--");
		return dao.insertData(data);
	}
	public List<Product> queryData( Product param)
	{
		System.out.println("---queryData--");
		return dao.queryData(param);
	}
}
