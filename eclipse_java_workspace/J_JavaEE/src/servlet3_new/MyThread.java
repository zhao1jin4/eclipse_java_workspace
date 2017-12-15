package servlet3_new;

import java.io.IOException;

import javax.servlet.AsyncContext;

public class MyThread extends Thread{

	AsyncContext context;
	
	public MyThread(AsyncContext context) 
	{
		super();
		this.context = context;
	}

	public void run() {
		try {
			context.getResponse().getWriter().write("在异步中的输出");
			Thread.sleep(3000);
			context.complete();//会调用  Listener的complete方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
