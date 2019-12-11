import javax.servlet.ServletContext;
import javax.servlet.ServletException;
 
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class MyWebApplicationInitializer implements WebApplicationInitializer 
{ 
	@Override
	public void onStartup(ServletContext context) throws ServletException {
	 
//		context.addListener(ContextLoaderListener.class);
//		context.setInitParameter("contextConfigLocation", "/WEB-INF/spring_annotation.xml");
		 
	}

}
