package alibaba.dubbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 	<dependency>
		<groupId>com.github.sgroschupf</groupId>
		<artifactId>zkclient</artifactId>
		<version>0.1</version>
	</dependency>
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>dubbo</artifactId>
		<version>2.5.3</version>
	</dependency>
			
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
		ApplicationContext context=new ClassPathXmlApplicationContext("alibaba/dubbo/dubbo-*.xml");
		DubboFacade dubboFacade=(DubboFacade)context.getBean("dubboFacade");
				
		QueryReq req=new QueryReq();//要i mplements Serializable
		req.setQueryId("1000010");;
		
		QueryRes res=dubboFacade.queryService(req);
		System.out.println(res.getData());
	}
}
