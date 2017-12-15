package myservlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MyFilter implements Filter{
	public void init(FilterConfig config) throws ServletException {
		String str=config.getInitParameter("blacklist-file");
		System.out.println("MyFilter___init,init-param blacklist-file="+str);
	}
	public void destroy() {
		System.out.println("MyFilter___destroy");
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter___before doFilter");
		
		chain.doFilter(new MyRequestWrapper((HttpServletRequest)request), response);
		
		System.out.println("MyFilter___after doFilter");
	}
}
