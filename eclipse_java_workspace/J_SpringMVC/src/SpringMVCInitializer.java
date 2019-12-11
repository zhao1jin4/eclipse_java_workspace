import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Ïàµ±ÓÚweb.xml
public class SpringMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{ 
	//springÈÝÆ÷
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
