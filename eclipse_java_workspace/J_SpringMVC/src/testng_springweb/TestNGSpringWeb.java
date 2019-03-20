package testng_springweb;

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


@WebAppConfiguration("file:WebContent/")  //����ע�� WebApplicationContext Ҫ��  @ContextConfiguration һ��ʹ��
@ContextConfiguration(locations = { "classpath:/testng_springweb/springTestNGWeb.xml" })
public class TestNGSpringWeb extends AbstractTestNGSpringContextTests {

	@Autowired
	private GenericApplicationContext ctx; // ��GenericApplicationContext

	@Autowired
	private WebApplicationContext wac;
	
	@Resource(name = "userService")
	private UserServiceImpl userService;// ���Զ�ע��Bean

	@BeforeClass
	public void init() {
		System.out.println("@BeforeClass,ctx="+ctx);//��ֵ��,maven��Ŀspring.xmlҪ����resourcesĿ¼�²���
		System.out.println("@BeforeClass,wac="+wac);//��ֵ��
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
		System.out.println("+++wac=" + wac);
		System.out.println("+++" + userService.getDao().getTest());
	}

}
