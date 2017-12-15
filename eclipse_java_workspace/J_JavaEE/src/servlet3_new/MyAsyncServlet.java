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

 
//@WebServlet(urlPatterns = {"/async"} ,asyncSupported=true)
public class MyAsyncServlet extends HttpServlet {
	 
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	
		AsyncContext ctx=request.startAsync();//现在又报错了????
		ctx.addListener(new AsyncListener()
			{
					public void onComplete(AsyncEvent event) throws IOException 
					{
						System.out.println("onComplete DONE");
					}
					public void onError(AsyncEvent event) throws IOException 
					{
					}
					public void onStartAsync(AsyncEvent event) throws IOException
					{
					}
					public void onTimeout(AsyncEvent event) throws IOException 
					{
					}  
			});
		MyThread my=new MyThread(ctx);
		my.start();
		response.getWriter().println("TestServlet3");
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
