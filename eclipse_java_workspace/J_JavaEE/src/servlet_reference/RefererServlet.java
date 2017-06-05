package servlet_reference;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/referer")
public class RefererServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
		{
		Enumeration<String> all = request.getHeaderNames();
		while(all.hasMoreElements())
		{
			System.out.println("Header:"+all.nextElement() );
		}
		String method=request.getMethod();
		String referer=request.getHeader("referer");//Referer,实际是小写,但传大写也OK
		String userAgent=request.getHeader("User-Agent");
		String cookie=request.getHeader("Cookie");
		
		System.out.println("User-Agent="+userAgent);
		System.out.println("method="+method);
		System.out.println("Cookie="+cookie);
		System.out.println("referer="+referer);
		
 		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if(referer==null ||   !referer.startsWith("http://localhost:8080/J_JavaEE"))
		{
			 response.sendRedirect("steal.html");  
	         return;  
		}else
			response.getWriter().write("没有盗链");
	}
}
