package easymock.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class DaoPostProcessor implements BeanPostProcessor 
{
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException 
	{
		if("myDao".equals(beanName) ||bean instanceof  MyDao)
		{
			IMocksControl mocksControl = EasyMock.createStrictControl();
			MyDao clientMock=mocksControl.createMock(MyDao.class);
			
			{
				Product param=new Product();
				param.setType("生活用品");
				
				List<Product> dataSet=new ArrayList<Product>();
				for(int i=0;i<3;i++)
				{
					Product product=new Product();
					product.setId(10+i);
					product.setName("产品"+i);
					product.setType(param.getType());
					dataSet.add(product);
				}
				
				//---List中的Product要重写equals方法
				//EasyMock.expect(clientMock.insertData(dataSet)).andReturn((long)dataSet.size());
				//EasyMock.expect(clientMock.queryData(param)).andReturn(dataSet);
				
				//--不用重写equals方法
				clientMock.insertData(EasyMock.isA(List.class));//有isNull(List.class),基本类型EasyMock.anyLong()
				EasyMock.expectLastCall().andStubReturn((long)dataSet.size());
			}
			
			
			{//匹配指定参数
				Product param=new Product();
				param.setType("生活用品");
				
				List<Product> queryData=new ArrayList<Product>();
				for(int i=0;i<3;i++)
				{
					Product product=new Product();
					product.setId(10+i);
					product.setName("产品"+i);
					product.setType("生活用品");
					queryData.add(product);
				}
				
				clientMock.queryData(param);
				EasyMock.expectLastCall().andStubReturn(queryData);
			}
			{//匹配所有参数 放在最后
				List<Product> queryData=new ArrayList<Product>();
				Product product=new Product();
				product.setId(901);
				product.setName("产品x");
				product.setType("xx用品");
				queryData.add(product);
				
				clientMock.queryData(EasyMock.anyObject(Product.class));//anyObject匹配传入null,isA不能匹配null
//				clientMock.queryData(
//						(Product) EasyMock.or(EasyMock.isA(Product.class),
//									EasyMock.isNull() )
//						);
				EasyMock.expectLastCall().andStubReturn(queryData);
			}
			
			
			
			EasyMock.replay(clientMock);
			return clientMock;
		}else
			return bean;
	}
	
	public static MyDao resetQuerytReturnValue(MyDao clientMock,String type)
	{
		List<Product> dataSet=new ArrayList<Product>();
		for(int i=0;i<3;i++)
		{
			Product product=new Product();
			product.setId(10+i);
			product.setName("产品"+i);
			product.setType(type);
			dataSet.add(product);
		}
		
		EasyMock.reset(clientMock);
		clientMock.queryData(EasyMock.anyObject(Product.class));//可以传null
		EasyMock.expectLastCall().andStubReturn(dataSet );
		EasyMock.replay(clientMock);
		return clientMock;
	}
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
	{
		return bean;
	}
}
