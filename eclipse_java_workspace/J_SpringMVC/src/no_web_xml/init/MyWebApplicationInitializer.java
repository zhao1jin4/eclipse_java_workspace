package no_web_xml.init;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
/*
public class MyWebApplicationInitializer implements  WebApplicationInitializer
{
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException
	{
		String configClazz=String.join(",", MvcConfig.class.getName(),MyBatisConfig.class.getName());
		Dynamic dync=servletContext.addServlet("spring_mvc", DispatcherServlet.class);
		dync.setLoadOnStartup(1);
		dync.setInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configClazz);
		dync.setInitParameter(ContextLoader.CONTEXT_CLASS_PARAM, AnnotationConfigWebApplicationContext.class.getName());
		dync.addMapping("*.mvc");
//		servletContext.setInitParameter(ContextLoader.CONTEXT_CLASS_PARAM, AnnotationConfigWebApplicationContext.class.getName());
//		servletContext.setInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configClazz);
//		servletContext.addListener(ContextLoaderListener.class);
	}
}
*/
 
 