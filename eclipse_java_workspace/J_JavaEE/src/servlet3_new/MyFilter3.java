package servlet3_new;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebFilter(filterName = "myFilter", urlPatterns = { "/dynServ","/test1" },asyncSupported=true)
//被动态配置的
public class MyFilter3 implements Filter{
	public void destroy() {
		
	}
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter3 before");
		chain.doFilter(req, resp);
		System.out.println("MyFilter3 after");
	}
	public void init(FilterConfig config) throws ServletException {
		System.out.println("MyFilter3 init");
	}

}
