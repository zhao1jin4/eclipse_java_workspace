package test_mockmvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MyDao {
	public long insertData(List<Product> data)
	{ 
		System.out.println("这是在Dao方法中");
		return data.size();
	}
	
	public List<Product> queryData( Product param)
	{
		System.out.println("这是在Dao方法中");
		
		List<Product> dataSet=new ArrayList<Product>();
		for(int i=0;i<3;i++)
		{
			Product product=new Product();
			product.setId(10+i);
			product.setName("产品"+i);
			product.setType(param.getType());
			dataSet.add(product);
		}
		return dataSet;
	}
}
