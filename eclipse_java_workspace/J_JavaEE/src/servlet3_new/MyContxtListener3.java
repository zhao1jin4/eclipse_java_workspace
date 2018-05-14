package servlet3_new;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContxtListener3 implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext context=event.getServletContext();
		//动态注册Servlet 
		ServletRegistration.Dynamic dynServ=context.addServlet("myServName", MyAsyncServlet.class);
		dynServ.setInitParameter("myparam", "myvalue");//servlet重写init方法，用ServletConfig取
		dynServ.addMapping("/async");
		dynServ.setAsyncSupported(true);
		
		//动态注册Filter
		FilterRegistration.Dynamic  dynFilter=context.addFilter("myFileterName",MyFilter3.class);
		dynFilter.setAsyncSupported(true);
		dynFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST) , true, "/async");//true=isMatchAfter 
		
		System.out.println("ServletContextListener(MyContxtListener3)___contextInitialized");
	}
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("ServletContextListener(MyContxtListener3)___contextDestroyed");
	}
}
