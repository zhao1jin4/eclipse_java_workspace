import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;


public class MyWebApplicationInitializer implements WebApplicationInitializer 
{
	@Override
	public void onStartup(ServletContext context) throws ServletException {
//		context.addListener(ContextLoaderListener.class);
//		context.setInitParameter("contextConfigLocation", "/WEB-INF/spring_annotation.xml");
		org.springframework.ui.freemarker.FreeMarkerConfigurationFactory x;
	}

}
