package test_mockmvc;
 
 

import org.junit.Before;
import org.junit.Test; 
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
 

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
@RunWith(SpringJUnit4ClassRunner.class)  
//@ActiveProfiles({"test"})
//@Transactional
@WebAppConfiguration //SpringMVC利用MockMvc进行单元测试 
@ContextConfiguration(locations={
		"classpath:test_mockmvc/spring-mockmvc.xml",
		})
public class MockITO_MockMvcTest  {

//	@InjectMocks //会进入方法体中
//	@Autowired
	
	@Mock
	private MyServiceBean myServiceBean;

	 
	@Autowired
	private WebApplicationContext wac;

	public MockMvc mockMvc; //mockMvc 

	ThreadLocalRandom random = ThreadLocalRandom.current();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		MockitoAnnotations.initMocks(this);

		com.fasterxml.classmate.TypeResolver x;
		net.bytebuddy.dynamic.loading.ClassLoadingStrategy y;
		org.objenesis.ObjenesisStd z;
	}
 
	@Test
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
		List<Product>  res=myServiceBean.queryData(new Product());
		System.out.println(res);
	}
	
	@Test
	public void testMVC()throws Exception  //测试 OK 
	{
		//spring-mockmvc.xml 有正启动的全部内容
		ResultActions resultActions = mockMvc.perform(
				post("/json/queryEmployeeVO.mvc")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"employee_id\":123,\"first_name\":\"李四\"}")
				)
				.andExpect(status().isOk())
				.andDo(print());
		MvcResult mvcResult = resultActions.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}
}
