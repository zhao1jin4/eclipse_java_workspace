package mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.ArgumentMatchers;

public class MockITOMain {
/*
 * <dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>2.16.0</version>
</dependency>

 */
	
	// Spring MVC�� �� Web
	public static void main(String[] args) {
//		MyServiceBean myServiceBean = new MyServiceBean();
		//myServiceBean.setDao(new MyDao());
		
		List<Product> dataSet=new ArrayList<Product>();
		for(int i=0;i<3;i++)
		{
			Product product=new Product();
			product.setId(10+i);
			product.setName("��Ʒ"+i);
			product.setType("������Ʒ");
			dataSet.add(product);
		}
		//ģ�ⷵ��ֵ
		MyServiceBean myServiceBean = mock(MyServiceBean.class);   
		when(myServiceBean.queryData(any(Product.class))).thenReturn(dataSet); 
		List<Product>  res=myServiceBean.queryData(new Product());
		System.out.println(res);

		reset(myServiceBean);//����ָ����bean������¼��  
		
		//ģ�����쳣
		when(myServiceBean.insertData(ArgumentMatchers.anyList())).thenThrow(RuntimeException.class);
		myServiceBean.insertData(Arrays.asList(new Product()));
		
	
	}

}
