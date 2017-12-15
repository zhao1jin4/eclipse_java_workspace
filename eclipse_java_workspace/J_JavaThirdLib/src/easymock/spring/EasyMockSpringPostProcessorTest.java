package easymock.spring;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {
	"classpath:/easymock/spring/spring.xml" ,
	"classpath:/easymock/spring/bean_post.xml" 
	}) 
public class EasyMockSpringPostProcessorTest // extends TestCase 
{
	@Autowired
	private ApplicationContext ctx;
	
	@Resource(name="myServiceBean")
	private MyServiceBean bean;
	
	@Test
	public  void testMockSpringObject() throws Exception 
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
		//---ProductҪ��дequals����
		//Assert.assertEquals(dataSet.size(), bean.insertData(dataSet));
		//Assert.assertEquals(dataSet, bean.queryData(param));
		//---
		Assert.assertEquals(dataSet.get(0).getName(), bean.queryData(param).get(0).getName());
		Assert.assertEquals("��Ʒx", bean.queryData(null).get(0).getName());
		Assert.assertEquals(dataSet.size(), bean.insertData(dataSet));
		
		
	}
	
	@Test
	public  void testReplaceSpringDao2() throws Exception 
	{
		List<Product> dataSet=bean.queryData(null);
		for(Product product:dataSet)
		{
			System.out.println(product.getId()+"="+product.getType());
		}
		System.out.println( "=========" );
		MyDao dao=ctx.getBean("myDao",MyDao.class);//��ͬһ������ ����reset,���÷���ǰ��������������ʲô
		DaoPostProcessor.resetQuerytReturnValue(dao, "����");
		 dataSet=dao.queryData(null);
		for(Product product:dataSet)
		{
			System.out.println(product.getId()+"="+product.getType());
		}
	}
}
