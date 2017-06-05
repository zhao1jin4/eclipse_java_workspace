package spring_jsp.extention;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor extends HandlerInterceptorAdapter
{
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception //˳��3,�������
	{
		System.out.println("=====afterCompletion");
	}
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception
	{ //��ModelAndView
		System.out.println("=====postHandle");//˳��2
	}
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
				Object handler) throws Exception//˳��1
	{
		System.out.println("=====preHandle");
		
		boolean flag=true;
		/*
		 if (request.getRequestURI().endsWith("lo.htm"))
		{
			flag=true;
			System.out.println("=====preHandle :TRUE");
		}else
		{
			System.out.println("=====preHandle:FALSE");
			flag=false;
		}
		*/
		return flag;//�Ƿ��ж�ִ����
	}
}
