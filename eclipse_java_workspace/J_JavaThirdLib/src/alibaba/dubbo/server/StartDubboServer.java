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
		
	}

}
