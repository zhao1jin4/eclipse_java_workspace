package org.liukai.tutorial.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.liukai.tutorial.service.HelloService;
import org.liukai.tutorial.service.HelloServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Controller
@RequestMapping("/main")
public class MainController {
	protected static Logger logger = Logger.getLogger("controller");

	/**
	 * 跳转到commonpage页面
	 */
	@RequestMapping(value = "/common", method = RequestMethod.GET)
	public String getCommonPage() {
		logger.debug("Received request to show common page");
		return "commonpage";
	}

	/**
	 * 跳转到adminpage页面
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String getAadminPage() {
		logger.debug("Received request to show admin page");
		return "adminpage";

	}
	@RequestMapping(value = "/anony", method = RequestMethod.GET)
	public void anony(HttpServletRequest request, HttpServletResponse response) 
	{
		WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		HelloService service=(HelloService)ctx.getBean("hello");
		if(request.getParameter("method").equals("init"))
		{
			service.init();
		}
		if(request.getParameter("method").equals("destroy"))
		{
			service.destroy();
		}
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response) {

		WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		HelloService service=(HelloService)ctx.getBean("hello");
		try
		{
			if(request.getParameter("method").equals("getHello"))
			{
				response.getWriter().println("get Hello :"+service.getHello());
				
			}else if(request.getParameter("method").equals("setHello"))
			{
				service.setHello(" GOOD ");
				response.getWriter().println("call set Hello good success");
			}
			if(request.getParameter("method").equals("initAdmin"))
			{
				service.initAdmin();
			}
			if(request.getParameter("method").equals("other"))
			{
				service.other();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
