package testng_springweb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import no_web_xml.init.MvcConfig;
import no_web_xml.init.MyBatisConfig;
@WebAppConfiguration("file:WebContent/")  // ����ע�� WebApplicationContext Ҫ�� @ContextConfiguration һ��ʹ��
@ContextConfiguration(locations = { "classpath:/testng_springweb/springTestNGMockMvc.xml" })
//@ContextConfiguration (classes = {MvcConfig.class,MyBatisConfig.class}) //���� MyWebApplicationInitializer����web.xml
public class TestNGMockMvc extends AbstractTestNGSpringContextTests {

	MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	
	@BeforeClass
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		//MockitoAnnotations.initMocks(this);//��ʲô�ã�
	}
	@Test
	//@Sql("init.sql")
	public void testRealRequest()throws Exception  
	{
		//��������ķ���
		ResultActions resultActions = mockMvc.perform(
					post("/json/queryEmployeeVO.mvc")
					.characterEncoding("UTF-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"employee_id\":123,\"first_name\":\"����1\"}")
				)//perform����ʵ�ĵ�����
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				//.andExpect(content().contentType("application/json")) //��������� expected:<application/json> but was:<application/json;charset=UTF-8>
				
				.andExpect(jsonPath("$.underEmp[0].first_name").value("li"))
				//���� com.jayway.jsonpath.Predicate ,   json-smart-2.3.jar , asm-1.0.2.jar(conflict-lib)
				.andExpect(jsonPath("$.underEmp[?(@.first_name == 'li')]").exists())  
				.andDo(print());
		MvcResult mvcResult = resultActions.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}

}
