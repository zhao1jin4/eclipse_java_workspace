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
		 //eclipse������  com.alibaba.dubbo.container.Main ������ -Ddubbo.properties.file=alibaba/dubbo/server/dubbo.properties -Ddubbo.spring.config=classpath:alibaba/dubbo/server/dubbo-server.xml
		 /*
		  *   �Զ�����META-INF/springĿ¼�µ�����Spring���á� 
			    ���ã�(����java����-D��������dubbo.properties��) 
			   dubbo.spring.config=classpath*:META-INF/spring/*.xml ----����spring���ü���λ�� 
		  */
	}

}
