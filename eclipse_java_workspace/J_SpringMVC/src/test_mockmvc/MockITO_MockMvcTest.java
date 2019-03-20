package test_mockmvc;
 
 

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
@WebAppConfiguration //可以注入 WebApplicationContext
@ContextConfiguration(locations={
		"classpath:test_mockmvc/spring-mockmvc.xml",
		})
public class MockITO_MockMvcTest  {

//	@InjectMocks //会进入方法体中
//	@Autowired
	
	//MockitoJUnitRunner x; Mockito的类
	
	@Mock //方式一    可以不是 Spring的Bean 
	private MyServiceBean myServiceBean;  

	 
	@Autowired
	private WebApplicationContext wac;

	public MockMvc mockMvc; //org.springframework.test.web.servlet.MockMvc 

	ThreadLocalRandom random = ThreadLocalRandom.current();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		MockitoAnnotations.initMocks(this);

		//myServiceBean = mock(MyServiceBean.class);//方式二  可以不是 Spring的Bean 
		
//		com.fasterxml.classmate.TypeResolver x;
//		net.bytebuddy.dynamic.loading.ClassLoadingStrategy y;
//		org.objenesis.ObjenesisStd z;
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
	public void testMVC()throws Exception  
	{
		/*
		 spring-mockmvc.xml 有
			<context:component-scan base-package="test_mockmvc"></context:component-scan>
			<context:component-scan base-package="spring_jsp.annotation" /> 
			<mvc:annotation-driven  validator="validator" />
		*/
		//不用http服务器
		ResultActions resultActions = mockMvc.perform(
					post("/json/queryEmployeeVO.mvc")
					.characterEncoding("UTF-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"employee_id\":123,\"first_name\":\"李四1\"}")
				)//这里就开始调用了
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				//.andExpect(content().contentType("application/json")) //如用这个报 expected:<application/json> but was:<application/json;charset=UTF-8>
				
				.andExpect(jsonPath("$.underEmp[0].first_name").value("li"))
				//依赖 com.jayway.jsonpath.Predicate ,   json-smart-2.3.jar , asm-1.0.2.jar(conflict-lib)
				.andExpect(jsonPath("$.underEmp[?(@.first_name == 'li')]").exists())  
				.andDo(print());
		MvcResult mvcResult = resultActions.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void testResetClient() //reset 的测试
	{ 
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
		mockServer.expect(requestTo("/greeting")).andRespond(withSuccess()); 
		mockServer.verify();
	}
	 
}
