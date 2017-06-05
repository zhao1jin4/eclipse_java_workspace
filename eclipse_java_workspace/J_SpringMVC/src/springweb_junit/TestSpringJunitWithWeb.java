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
//  value="/WEB-INF/spring_annotation.xml"   ,  ���ܶ�/WEB-INF��?????
@WebAppConfiguration("file:WEB-INF/spring_annotation.xml")  //����ע�� WebApplicationContext Ҫ��  @ContextConfiguration һ��ʹ��
@ContextConfiguration()
public class TestSpringJunitWithWeb 
{
	@Autowired
	private WebApplicationContext wac;
	
	@Test
	public void testWeb() {
		String zh=wac.getMessage("heading",null, new Locale("zh"));
		Assert.assertEquals("ͷ", zh);
		String en=wac.getMessage("heading",null, new Locale("en"));
		Assert.assertEquals("heading", en);
		
		//wac.getBean("x");
	}
}
