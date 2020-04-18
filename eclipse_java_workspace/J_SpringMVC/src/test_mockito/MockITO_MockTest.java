package test_mockito;
 
 

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)  
//@ActiveProfiles({"test"})
//@Transactional
@ContextConfiguration(locations={
		"classpath:test_mockito/spring-mockmvc.xml",
		})
public class MockITO_MockTest  {
//	@InjectMocks //会进入方法体中
//	@Autowired
	//MockitoJUnitRunner x; Mockito的类
	
	@Mock //  可以不是 Spring的Bean 
	private MyServiceBean myServiceBean;  


	ThreadLocalRandom random = ThreadLocalRandom.current();//什么用？
	@Before
	public void setup() {
		myServiceBean = mock(MyServiceBean.class);//可以不是 Spring的Bean 
	}
 
	@Test
	//@Sql("init.sql")
	public void testService() throws Exception  //测试 OK 
	{ 
		//spring-mockmvc.xml 只有	<context:component-scan base-package="test_mockmvc"></context:component-scan>
		List<Product> dataSet=new ArrayList<Product>();
		for(int i=0;i<3;i++)
		{
			Product product=new Product();
			product.setId(10+i);
			product.setName("产品"+i);
			product.setType("生活用品");
			dataSet.add(product);
		}
		when(myServiceBean.queryData(any(Product.class))).thenReturn(dataSet); 
		List<Product>  res=myServiceBean.queryData(new Product());//没有真实调用 
		System.out.println(res);
		
		reset(myServiceBean);//重置指定的bean的所有录制
		
		res=myServiceBean.queryData(new Product());
		System.out.println(res);
		//模拟抛异常
		try {
			when(myServiceBean.insertData(ArgumentMatchers.anyList())).thenThrow(RuntimeException.class);
			myServiceBean.insertData(Arrays.asList(new Product()));
		}catch(Exception e)
		{
			System.err.println("error have :"+e );
		}
	}
	 
	@Test
	public void testRestTemplate() 
	{ 
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
		mockServer.expect(requestTo("/greeting")).andRespond(withSuccess()); 
		
		String res=restTemplate.getForObject("/greeting", String.class);//没有真的请求
		System.out.println(res);//没有返回值 ，但没有报错
		
		mockServer.verify();
	}
}
