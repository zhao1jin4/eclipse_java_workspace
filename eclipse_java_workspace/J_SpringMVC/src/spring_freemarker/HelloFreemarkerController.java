package spring_freemarker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;

public class HelloFreemarkerController implements Controller
{
	protected final Log logger = LogFactory.getLog(getClass());
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nowx = (new java.util.Date()).toString();
		ModelAndView mv=	new ModelAndView();
		mv.addObject("now", nowx);
		mv.addObject("num",123456);//防止出现,
		
		mv.addObject("freemarkerBean",new FreemarkerUtil()); 
		
		freemarker.ext.beans.BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_21); 
//		builder.setUseModelCache(true);
//		builder.setExposeFields(true); 
		freemarker.ext.beans.BeansWrapper beansWrapper = builder.build();
//		freemarker.ext.beans.BeansWrapper.getDefaultInstance();//deprecate
		mv.addObject("statics", beansWrapper.getStaticModels()); 
		//一行写法
		//new  freemarker.ext.beans.BeansWrapperBuilder(freemarker.template.Configuration.VERSION_2_3_21).build().getStaticModels(); 
		mv.setViewName("hello");
		
		
		
		return mv;
	}
}