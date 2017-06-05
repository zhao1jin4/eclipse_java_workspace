package util.thread;

import java.util.concurrent.CountDownLatch;


public class NoticedThread extends Thread
{
	private CountDownLatch latch;
	public NoticedThread(ThreadGroup group,Runnable target,String name,CountDownLatch latch)
	{
		super(group,target,name);
		this.latch=latch;
	}
	public void run()
	{
		try 
		{
			super.run();
		} finally{ //不用catch ,有threadGroup处理
			latch.countDown();//但防止计数错误
		}
	}
}
