package spring_testng;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


//SpringMVC中有是web项目
//@WebAppConfiguration("file:WebContent/")  //可以注入 WebApplicationContext 要和  @ContextConfiguration 一起使用
@ContextConfiguration(locations = { "classpath:/spring_testng/springTestNG.xml" })
public class TestNGSpring extends AbstractTestNGSpringContextTests {

	@Autowired
	private GenericApplicationContext ctx; // 是GenericApplicationContext

//	@Autowired
//	private WebApplicationContext wac;
	
	@Resource(name = "userService")
	private UserServiceImpl userService;// 可自动注入Bean

	@BeforeClass
	public void init() {
		System.out.println("@BeforeClass,ctx="+ctx);//有值的,maven项目spring.xml要放在resources目录下才行
		//System.out.println("@BeforeClass,wac="+wac);//有值的
	}

	@AfterClass
	public void destory() {
		System.out.println("@AfterClass");
	}

	@Test
	public void testMethod1() {
		String email = "abc";
		Assert.assertNotNull(email);
		// Assert.assertEquals(email, "123@test.com");

		System.out.println("+++ctx=" + ctx);
//		System.out.println("+++wac=" + wac);
		System.out.println("+++" + userService.getDao().getTest());
	}

}
