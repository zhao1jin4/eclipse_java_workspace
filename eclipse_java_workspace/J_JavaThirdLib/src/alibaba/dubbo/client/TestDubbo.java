package alibaba.dubbo.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import alibaba.dubbo.server.DubboFacade;
import alibaba.dubbo.server.QueryReq;
import alibaba.dubbo.server.QueryRes;

/*
 
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>dubbo</artifactId>
		<version>2.5.3</version>
	</dependency>
	
	zkclient
	javassist
	netty 3.10
	
	jdk 1.7
	
 */


//dubbo-admin-2.5.3.war 不能下载 ???

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration( {
//	"classpath*:alibaba/dubbo/dubbo-*.xml",
//	}) 
public class TestDubbo
{
//	private DubboFacade dubboFacade; //为何注入不进来???
//	public void setDubboFacade(DubboFacade dubboFacade) {
//		this.dubboFacade = dubboFacade;
//	}

	//使用与webService一样,像RMI
	@Test
	public void testDubboClient()
	{
		org.jboss.netty.channel.ChannelFactory x;
		System.setProperty("dubbo.properties.file", "alibaba/dubbo/client/dubbo.properties");
		
		
		ApplicationContext context=new ClassPathXmlApplicationContext("alibaba/dubbo/client/dubbo-client.xml");
		DubboFacade dubboFacade=(DubboFacade)context.getBean("dubboFacade");
				
		QueryReq req=new QueryReq();//要implements Serializable
		req.setQueryId("1000010");;
		
		QueryRes res=dubboFacade.queryService(req);
		System.out.println(res.getData());
	}
}
