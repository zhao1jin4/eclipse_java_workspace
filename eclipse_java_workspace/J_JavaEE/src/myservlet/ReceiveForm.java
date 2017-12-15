package myservlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/receiveForm")
//为HttpClient
public class ReceiveForm extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("-----------doGet");
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("-----------doPost");
		
		request.setCharacterEncoding("UTF-8"); 

		System.out.println("服务收到的请求头:");
		Enumeration<String> enumeration=  request.getHeaderNames();
		while(enumeration.hasMoreElements())
		{
			String name=enumeration.nextElement();
			System.out.println(name+"="+ request.getHeader(name));
		}
		
		String cn=request.getParameter("username");
		System.out.println("username="+cn);//和request.setCharacterEncoding("UTF-8"); 成对使用 
		//System.out.println("username="+new String(cn.getBytes("iso8859-1"),"UTF-8"));
		
		System.out.println("password="+request.getParameter("password"));
		
		request.getSession().setAttribute("userId", cn);
		
		//response.setCharacterEncoding("UTF-8");//好像没效果
		response.addHeader("Content-Type", "text/plain;charset=UTF-8");//对浏览器有用,对HttpClient应该没用吧
		//response.setContentType("text/html;charset=UTF-8");
		response.setBufferSize(1024);
		
		response.getOutputStream().write("doPost的响应".getBytes("UTF-8"));
		//这里对应httpClient端,windows下默认为GBK,和文件编码没有关系
	}
}
