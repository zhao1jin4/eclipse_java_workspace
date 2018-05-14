package servlet3_new;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;

public class MyThread extends Thread{

	AsyncContext context;
	
	public MyThread(AsyncContext context) 
	{
		super();
		this.context = context;
	}

	public void run() {
		try {
			ServletResponse resp=context.getResponse();
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().write("在异步中的输出");
			Thread.sleep(3000);
			
			context.complete();//会调用  Listener的complete方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
