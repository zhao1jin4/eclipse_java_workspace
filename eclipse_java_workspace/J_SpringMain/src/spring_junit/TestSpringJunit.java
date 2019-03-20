package spring_junit;

import javax.annotation.Resource;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.HierarchyMode;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.MultiValueMap;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {AppConfig.class, TestConfig.class})
@ContextConfiguration("classpath:spring_junit/springJunit.xml") 
//@TestExecutionListeners({CustomTestExecutionListener.class}) //implements TestExecutionListener,���˾Ͳ����Զ�ע����
//@ContextConfiguration //��ע�� ApplicationContext;
@ActiveProfiles("dev") //��Ӧ@Profile("dev")
public class TestSpringJunit 
{
	@Autowired
	private GenericApplicationContext ctx; //��GenericApplicationContext
	
	@Resource(name="userService")
	private UserServiceImpl userService;//ͨ��set�޸� userDao,��������
	
	@Resource(name="userDao") //��xml����id="userDAO",�������������,ע��
	private UserDAO userDAO;
	
	
	@BeforeClass
	public static void init()//������static
	{
		System.getProperties().put("test-groups", "integration-tests");
		System.out.println("@BeforeClass");
	}
	@AfterClass
	public static void destory()
	{
		System.out.println("@AfterClass");
	}
	
	  @Profile("dev")//ͬ<beans profile="dev"
	  @Configuration //ָ����� @ContextConfiguration���Բ�ָ������
	  static class Config 
	  {
	        @Bean(name="userDao")
	        public UserDAO newUserDAO() {
	        	UserDAO userDAO = new UserDAOImpl();
	            return userDAO;
	        }
	 }
	@Timed(millis=3000)
	@Repeat(1)
	@IfProfileValue(name="test-groups", values={"unit-tests"})
	@Test
	public void testSaveUseContext() {
		UserDAO userDao=(UserDAO)ctx.getBean("userDao");
		userDao.save("��");
	}
	
	@IfProfileValue(name="test-groups", values={"unit-tests"})
	@Test
	public void testSave() {
		this.userDAO.save("��");
	}
	
}




class CustomTestExecutionListener implements TestExecutionListener
{

	public void beforeTestClass(TestContext testContext) throws Exception {
		System.out.println("beforeTestClass");
	}

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		System.out.println("prepareTestInstance");
	}

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		System.out.println("beforeTestMethod");
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		System.out.println("afterTestMethod");
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		System.out.println("afterTestClass");
	}
	
}


