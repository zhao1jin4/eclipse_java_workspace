package servlet3_new;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
//@WebServlet(urlPatterns = {"/async"} ,asyncSupported=true)//被动态配置的
public class MyAsyncServlet extends HttpServlet {
 
	@Override
	public void init(ServletConfig config) throws ServletException {
		String myparam=config.getInitParameter("myparam");
		System.out.println(myparam);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException
	{
		
		AsyncContext ctx=request.startAsync();//走的Filter也要异步支持
		ctx.addListener(new AsyncListener()
			{
					public void onComplete(AsyncEvent event) throws IOException 
					{
						System.out.println("MyAsyncServlet onComplete  ");
					}
					public void onError(AsyncEvent event) throws IOException 
					{
						System.out.println("MyAsyncServlet onError ");
					}
					public void onStartAsync(AsyncEvent event) throws IOException
					{
						System.out.println("MyAsyncServlet onStartAsync");
					}
					public void onTimeout(AsyncEvent event) throws IOException 
					{
						System.out.println("MyAsyncServlet onTimeout");
					}  
			});
		MyThread my=new MyThread(ctx);
		my.start();
		response.getWriter().println(" Last Code in MyAsyncServlet");
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
