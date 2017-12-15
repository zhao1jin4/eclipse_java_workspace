package servlet3_new;

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
		ServletRegistration.Dynamic dynServ=context.addServlet("myServName", MyAsyncServlet.class);
		//dynServ.setInitParameter("debugl", "true");
		dynServ.addMapping("/async");
		dynServ.setAsyncSupported(true);
		//动态注册Servlet 
		FilterRegistration.Dynamic  dynFilter=context.addFilter("myFileterName",MyFilter3.class);//动态注册Filter
		//dynFilter.addMappingForUrlPatterns(DispatcherType.REQUEST , true, "/async");
		
		System.out.println("ServletContextListener___contextInitialized");
	}
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("ServletContextListener___contextDestroyed");
	}
}
