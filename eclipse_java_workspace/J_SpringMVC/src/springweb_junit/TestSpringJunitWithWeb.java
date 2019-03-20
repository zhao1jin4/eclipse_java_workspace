package springweb_junit;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class) 
@WebAppConfiguration("file:WebContent/")  //可以注入 WebApplicationContext 要和  @ContextConfiguration 一起使用
@ContextConfiguration("file:WebContent/WEB-INF/spring_annotation.xml")
public class TestSpringJunitWithWeb 
{
	@Autowired
	private WebApplicationContext wac;
	
	@Test
	public void testWeb() {
		String zh=wac.getMessage("heading",null, new Locale("zh"));
		Assert.assertEquals("头", zh);
		String en=wac.getMessage("heading",null, new Locale("en"));
		Assert.assertEquals("heading", en);
		
		//wac.getBean("x");
	}
}
