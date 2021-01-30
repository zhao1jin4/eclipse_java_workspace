package json_valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalException {
	@ExceptionHandler(BindException.class)
	public Object commonException(BindException exception )
	{
		System.err.println(exception);
		BindingResult result=exception.getBindingResult() ;
		List<String> errList=new ArrayList<>();
		if(result.hasErrors())
		{
			for(ObjectError err:result.getAllErrors())
			{
				System.out.println(err.getObjectName()+"=="+err.getDefaultMessage());
				errList.add(err.getDefaultMessage());
			}
				 //代码中的国际化方法,也可 @Autowired private MessageSource messageSource;
//				ServletContext servletContext=request.getServletContext();
//				WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  //web.xml中要必须有ContexLoaderListener注册过
//				Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);  
//			    String title = applicationContext.getMessage("title",getRequiredWebApplicationContext, locale);  
//			    System.out.println("代码  国际化  title为:"+title);
		}
		return errList;
	}
	@ExceptionHandler(Exception.class)
	public Object commonException(Exception exception )
	{
		System.err.println(exception);
		return "Exception返回"+exception.getMessage();
	}
}
