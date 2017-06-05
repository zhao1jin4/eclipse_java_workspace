package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonThreadRestart {
	
	public static void main(String[] args) throws Exception {
	  Thread t=new  Thread(new MyRunnable());
	  t.start();
	  Thread.sleep(1000);
	  // t.start();//已经启动过不能再重新启动,要么再new一次，要么使用threadpool
//	  t=new Thread(new MyRunnable());
//	  t.start();
	  
	   ExecutorService singleThread=  Executors.newSingleThreadExecutor();
	   singleThread.submit(new MyRunnable());
	   singleThread.submit(new MyRunnable());
	   singleThread.submit(new MyRunnable());
	   singleThread.shutdown();
	}

}
class MyRunnable implements  Runnable
{
	@Override
	public void run() {
		System.out.println("在thread 线程中运行");
	}
}