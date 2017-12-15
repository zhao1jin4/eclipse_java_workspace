package spring_freemarker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloFreemarkerController implements Controller
{
	protected final Log logger = LogFactory.getLog(getClass());
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nowx = (new java.util.Date()).toString();
		ModelAndView mv=	new ModelAndView();
		mv.addObject("now", nowx);
		mv.addObject("num",123456);//∑¿÷π≥ˆœ÷,
		mv.setViewName("hello");
		return mv;
	}
}