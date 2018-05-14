package alibaba.dubbo.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import alibaba.dubbo.server.DubboFacade;
import alibaba.dubbo.server.DubboGroupVersionFacade;
import alibaba.dubbo.server.QueryReq;
import alibaba.dubbo.server.QueryRes;

/*
 
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>dubbo</artifactId>
		<version>2.6.0</version>
	</dependency>
	<dependency>
	    <groupId>com.esotericsoftware</groupId>
	    <artifactId>kryo</artifactId>
	    <version>4.0.1</version>
	</dependency> 
	 

	zkclient
	javassist
	netty 3.10
	
	jdk 1.7
	
 */
 

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
		com.esotericsoftware.kryo.io.Output kryo; 
		
		//org.jboss.netty.channel.ChannelFactory x;
		System.setProperty("dubbo.properties.file", "alibaba/dubbo/client/dubbo.properties");
		
		
		ApplicationContext context=new ClassPathXmlApplicationContext("alibaba/dubbo/client/dubbo-client.xml");
		DubboFacade dubboFacade=(DubboFacade)context.getBean("dubboFacade");
				
		QueryReq req=new QueryReq();//要implements Serializable
		req.setQueryId("1000010");;
		//这之前如服务端没有启也不会报错，原因配置了 check="false"
		QueryRes res=dubboFacade.queryService(req);
		System.out.println(res.getData());
	}
	
	@Test
	public void testDubboGroupClient() 
	{
		System.setProperty("dubbo.properties.file", "alibaba/dubbo/client/dubbo.properties");
		ApplicationContext context=new ClassPathXmlApplicationContext("alibaba/dubbo/client/dubbo-client.xml");
		DubboGroupVersionFacade dubboFacade=(DubboGroupVersionFacade)context.getBean("dubboGroupVersionFacade");
				
		QueryReq req=new QueryReq();//要implements Serializable
		req.setQueryId("1000010");;
		//这之前如服务端没有启也不会报错，原因配置了 check="false"
		QueryRes res=dubboFacade.theGroupVersion(req);
		System.out.println(res.getData());
	}
	
	
}
