package spring3_annotation_config;


import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration("classpath:spring3_config/config_beans.xml")
public class TestUserDAO extends AbstractJUnit4SpringContextTests{
	
	@Resource(name="userDAO") //在xml中找id="userDAO",并赋下面的属性,注入
	private UserDAO userDAO;
	
	
	@Test
	public void testSave() {
		this.userDAO.save("李");
	}
}
