package alibaba.dubbo.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartDubboServer {

	public static void main(String[] args) {
		System.setProperty("dubbo.properties.file", "alibaba/dubbo/server/dubbo.properties");
		
		ApplicationContext context=new ClassPathXmlApplicationContext("alibaba/dubbo/server/dubbo-server.xml");
		
		System.out.println("DUBBO Server started");
		
		 try {
			Thread.sleep(1000*60*60*24*365*100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 
		 //----alibaba Main
		 //eclipse启动用  com.alibaba.dubbo.container.Main 参数传 -Ddubbo.properties.file=alibaba/dubbo/server/dubbo.properties -Ddubbo.spring.config=classpath:alibaba/dubbo/server/dubbo-server.xml
		 /*
		  *   自动加载META-INF/spring目录下的所有Spring配置。 
			    配置：(配在java命令-D参数或者dubbo.properties中) 
			   dubbo.spring.config=classpath*:META-INF/spring/*.xml ----配置spring配置加载位置 
		  */
	}

}
