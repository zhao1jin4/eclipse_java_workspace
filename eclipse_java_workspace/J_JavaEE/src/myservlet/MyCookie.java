package myservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cookieServ")
public class MyCookie   extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 使用JS判断 Cookie是否被禁用
		var c="jscookietest=valid";
		document.cookie=c;
		if(document.cookie.indexOf(c)==-1)
		*/
		Cookie cookie=new Cookie("website","http://bing.cn");
		cookie.setMaxAge(2000);
		resp.addCookie(cookie);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		req.getRequestDispatcher("ok.jsp").forward(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
