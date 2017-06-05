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


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {AppConfig.class, TestConfig.class})
@ContextConfiguration("classpath:spring_junit/springJunitEasyMock.xml") 
//@TestExecutionListeners({CustomTestExecutionListener.class}) //implements TestExecutionListener,加了就不能自动注入了
//@ContextConfiguration //可注入 ApplicationContext;
@ActiveProfiles("dev") //对应@Profile("dev")
public class TestSpringJunit 
{
	@Autowired
	private GenericApplicationContext ctx; //是GenericApplicationContext
	
	@Resource(name="userService")
	private UserServiceImpl userService;//通过set修改 userDao,其它做法
	
	@Resource(name="userDao") //在xml中找id="userDAO",并赋下面的属性,注入
	private UserDAO userDAO;
	
	
	@BeforeClass
	public static void init()//必须是static
	{
		System.getProperties().put("test-groups", "integration-tests");
		System.out.println("@BeforeClass");
	}
	@AfterClass
	public static void destory()
	{
		System.out.println("@AfterClass");
	}
	
	  @Profile("dev")//同<beans profile="dev"
	  @Configuration //指定这个 @ContextConfiguration可以不指定配置
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
		userDao.save("李");
	}
	
	@IfProfileValue(name="test-groups", values={"unit-tests"})
	@Test
	public void testSave() {
		this.userDAO.save("李");
	}
	
	@IfProfileValue(name="test-groups", values={"integration-tests"})
	@Test
	public void testMockDynamicChange()
	{
		
		String beanName="userDao";
//		UserDAO uDao=(UserDAO)ctx.getBean(beanName);
//		String resName=uDao.queryNameById("li");
//		String resultName=userDAO.queryNameById("li");
		
		DefaultListableBeanFactory  factory=(DefaultListableBeanFactory)ctx.getBeanFactory();//是DefaultListableBeanFactory
		
		{
			/*
			 Assert.assertEquals("newMock=defaultMockName", userDAO.getTest());//OK
			 UserDAO mockObj=UserDAOMock.generateMockObject("li");
			 Assert.assertEquals("newMock=li", mockObj.getTest());//OK
			 
			 factory.removeBeanDefinition(beanName);
			 factory.registerSingleton(beanName, mockObj); //不会修改已经注入的
			// Assert.assertEquals("newMock=li", userDAO.getTest());//Fail
			 
			 UserDAO springBean=(UserDAO)ctx.getBean(beanName);
			 Assert.assertEquals("newMock=li", springBean.getTest());//OK
			  
			userService.setDao(mockObj);//这样修改已经注入的
			//ReflectionTestUtils.setField(userService, "userDAO", mockObj, UserDAO.class);//用spring提供的方法注入aurowired的字段
		*/
			 //---
			 //ctx.refresh();//GenericApplicationContext不支持
		}
		
		//------------
		{
			UserDAO springBean=(UserDAO)ctx.getBean(beanName);
			Assert.assertEquals("newMock=defaultMockName", springBean.getTest());//OK
			UserDAOMock.resetMockObject(springBean, "wang");
			Assert.assertEquals("newMock=wang", springBean.getTest());
			Assert.assertEquals("newMock=wang",userService.getDao().getTest());//会修改Spring容器中的Bean,OK
		 
		}
		 //
		
		 
		
 
	}
	
}

