package shiro_web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.ui.ModelMap;

@WebServlet("/login.ser")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response); 
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*
		#登录表单提交地址要authc
		/login.ser=authc
		#/**=authc
		*/
		String exceptClassName=(String) request.getAttribute("shiroLoginFailure");
		if(exceptClassName!=null)
		{
			if(UnknownAccountException.class.getName().equals(exceptClassName))
			{
				request.setAttribute("error","用户名不存在");
			}else if(IncorrectCredentialsException.class.getName().equals(exceptClassName))
			{
				request.setAttribute("error","密码错误");
			}else 
			{
				request.setAttribute("error","系统异常");
			}
		}
		//如登录成功会直接跳到登录前的页面，如没有登录前的页面默认请求/
		request.getRequestDispatcher("/web/login.jsp").forward(request, response);
		
		System.out.println("LoginServlet");
		/*
		String username= request.getParameter("j_username");
		String password= request.getParameter("j_password");
		System.out.println("LoginServlet username="+username+"，password="+password);
	
		 if( 
			 (username.equals("lisi")&&password.equals("123"))
			 || (username.equals("wang")&&password.equals("456"))
		)
		 {
			 
			 request.getRequestDispatcher("main.jsp").forward(request, response);
		 }
		 else
		 {
			 request.setAttribute("error", "用户名密码错误");
			 request.getRequestDispatcher("login.jsp").forward(request, response);
		 }
		*/
		
	}
	
	
}

