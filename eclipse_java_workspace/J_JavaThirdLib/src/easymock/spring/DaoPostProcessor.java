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
				param.setType("������Ʒ");
				
				List<Product> dataSet=new ArrayList<Product>();
				for(int i=0;i<3;i++)
				{
					Product product=new Product();
					product.setId(10+i);
					product.setName("��Ʒ"+i);
					product.setType(param.getType());
					dataSet.add(product);
				}
				
				//---List�е�ProductҪ��дequals����
				//EasyMock.expect(clientMock.insertData(dataSet)).andReturn((long)dataSet.size());
				//EasyMock.expect(clientMock.queryData(param)).andReturn(dataSet);
				
				//--������дequals����
				clientMock.insertData(EasyMock.isA(List.class));//��isNull(List.class),��������EasyMock.anyLong()
				EasyMock.expectLastCall().andStubReturn((long)dataSet.size());
			}
			
			
			{//ƥ��ָ������
				Product param=new Product();
				param.setType("������Ʒ");
				
				List<Product> queryData=new ArrayList<Product>();
				for(int i=0;i<3;i++)
				{
					Product product=new Product();
					product.setId(10+i);
					product.setName("��Ʒ"+i);
					product.setType("������Ʒ");
					queryData.add(product);
				}
				
				clientMock.queryData(param);
				EasyMock.expectLastCall().andStubReturn(queryData);
			}
			{//ƥ�����в��� �������
				List<Product> queryData=new ArrayList<Product>();
				Product product=new Product();
				product.setId(901);
				product.setName("��Ʒx");
				product.setType("xx��Ʒ");
				queryData.add(product);
				
				clientMock.queryData(EasyMock.anyObject(Product.class));//anyObjectƥ�䴫��null,isA����ƥ��null
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
			product.setName("��Ʒ"+i);
			product.setType(type);
			dataSet.add(product);
		}
		
		EasyMock.reset(clientMock);
		clientMock.queryData(EasyMock.anyObject(Product.class));//���Դ�null
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
