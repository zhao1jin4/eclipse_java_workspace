package spring_jsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloWorldController implements Controller
{
	protected final Log logger = LogFactory.getLog(getClass());
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.debug("带HttpServletRequest ,请的URL是:"+request.getRequestURI());
		String nowx = (new java.util.Date()).toString();
		
		
		
		//return new ModelAndView("/helloWorld", "now", nowx);//可以不用加"/",对于使用UrlBasedViewResolver,找/WEB-INF/jsp/helloWorld.jsp
		//return new ModelAndView("/WEB-INF/jsp/helloWorld.jsp", "now", nowx);//对没有配置UrlBasedViewResolver的方式
		
		ModelAndView mv=	new ModelAndView();
		mv.addObject("now", nowx);
		mv.setViewName("helloWorld");
		return mv;
		
		//对使用ControllerClassNameHandlerMapping,请求路径名为Helloworld.mvc全小写,如没有视图名,默认视图名为helloworld
		//看日志会默认使用DefaultRequestToViewNameTranslator,InternalResourceViewResolver
	}
}