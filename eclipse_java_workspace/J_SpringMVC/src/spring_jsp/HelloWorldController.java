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
		logger.debug("��HttpServletRequest ,���URL��:"+request.getRequestURI());
		String nowx = (new java.util.Date()).toString();
		
		
		
		//return new ModelAndView("/helloWorld", "now", nowx);//���Բ��ü�"/",����ʹ��UrlBasedViewResolver,��/WEB-INF/jsp/helloWorld.jsp
		//return new ModelAndView("/WEB-INF/jsp/helloWorld.jsp", "now", nowx);//��û������UrlBasedViewResolver�ķ�ʽ
		
		ModelAndView mv=	new ModelAndView();
		mv.addObject("now", nowx);
		mv.setViewName("helloWorld");
		return mv;
		
		//��ʹ��ControllerClassNameHandlerMapping,����·����ΪHelloworld.mvcȫСд,��û����ͼ��,Ĭ����ͼ��Ϊhelloworld
		//����־��Ĭ��ʹ��DefaultRequestToViewNameTranslator,InternalResourceViewResolver
	}
}