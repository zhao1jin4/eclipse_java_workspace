package mockito;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
public class MyServiceBean 
{
	private MyDao dao;

	public void setDao(MyDao dao) {
		this.dao = dao;
	}
	
	public long insertData(List<Product> data)
	{
		return dao.insertData(data);
	}
	public List<Product> queryData( Product param)
	{
		return dao.queryData(param);
	}
}
