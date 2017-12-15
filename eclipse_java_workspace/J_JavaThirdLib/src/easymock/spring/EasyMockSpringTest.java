package easymock.spring;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {
	"classpath:/easymock/spring/spring.xml" 
	}) 
public class EasyMockSpringTest // extends TestCase 
{
	@Autowired
	private ApplicationContext ctx;
	
	@Resource(name="myServiceBean")
	private MyServiceBean bean;
	
	@Test
	public  void testReplaceSpringDao() throws Exception 
	{
		List<Product> dataSet=new ArrayList<Product>();
		for(int i=0;i<3;i++)
		{
			Product product=new Product();
			product.setId(10+i);
			product.setName("��Ʒ"+i);
			product.setType("������Ʒ");
			dataSet.add(product);
		}
		
		
		IMocksControl mocksControl = EasyMock.createStrictControl();
		MyDao mockDao=mocksControl.createMock(MyDao.class);//��class Ҫ objenesis.jar
		EasyMock.expect(mockDao.insertData(dataSet)).andReturn((long)dataSet.size());
		EasyMock.replay(mockDao);
		ReflectionTestUtils.setField(bean, "dao", mockDao, MyDao.class);//��spring�ṩ�ķ���ע��aurowired���ֶ�
		Assert.assertEquals(dataSet.size(), bean.insertData(dataSet));
		EasyMock.verify(mockDao);
		
	}
	
}
