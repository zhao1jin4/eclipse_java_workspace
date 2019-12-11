import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//�൱��web.xml
public class SpringMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{ 
	//spring����
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
//				Config.class
				} ;
	}
	//servletContext
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return  new Class[] {
//				WebConfig.class
		};
	}
	//url-mapping
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
}
