package spring_jasypt;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {AppConfig.class, TestConfig.class})
@ContextConfiguration("classpath:spring_jasypt/jasypt_spring.xml") 
public class JasyptSpringJunit 
{
	@Autowired
	private GenericApplicationContext ctx; //是GenericApplicationContext
	 
	@Value("${redis.password}")
	private String redisPass;//就解密的thePass
	
	@Value("#{configProperties.redis_password}") //自己的key不能有点
	private String redisPass2;
	
	@BeforeClass
	public static void init()//必须是static
	{ 
		System.out.println("@BeforeClass");
	}
	@AfterClass
	public static void destory()
	{
		System.out.println("@AfterClass");
	}
	
	  
	@Test
	public void testProperties() {
		System.out.println("redisPass="+redisPass);//就解密的thePass
		System.out.println("redisPass2="+redisPass2);
	}
	 
	
}


