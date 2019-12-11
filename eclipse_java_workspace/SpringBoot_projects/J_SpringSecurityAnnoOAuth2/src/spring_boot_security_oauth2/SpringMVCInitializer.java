package spring_boot_security_oauth2;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//相当于web.xml
public class SpringMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{ 
	//spring容器
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
				ApplicationConfig.class
				} ;
	}
	//servletContext
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return  new Class[] {
				MvcConfig.class,WebSecurityConfig.class
		};
	}
	//url-mapping
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
}