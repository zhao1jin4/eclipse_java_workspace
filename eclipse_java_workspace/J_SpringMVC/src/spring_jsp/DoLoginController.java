package spring_jsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class DoLoginController implements Controller
{
	protected final static Log LOG = LogFactory.getLog(DoLoginController.class);
	
	public static final String COOKIE_SESSION_NAME="SESSION";
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String username=request.getParameter("username");
		LOG.info("�յ��û���Ϊ"+username);
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0)
		{
			for(Cookie cookie:cookies)
			{
				if(cookie.getName().equals(COOKIE_SESSION_NAME))//SESSION
				{
					System.out.println("�ͻ��˵� cookie SESSION ID ��:"+cookie.getValue());
				}
				
			}
		}
		System.out.println("Servet API SESSION ID ��:"+request.getSession().getId());//spring sessionʹ�������Դ����  SessionRepositoryFilter
		request.getSession().setAttribute("login_user", username);
		//�Ͱ�session��ֵ�����redis��
		
		String nowx = (new java.util.Date()).toString();
	 	ModelAndView mv=	new ModelAndView();
		mv.addObject("now", nowx);
		mv.setViewName("helloWorld");
		
		return mv;
	}
}