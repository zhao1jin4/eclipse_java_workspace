package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonThreadRestart {
	
	public static void main(String[] args) throws Exception {
	  Thread t=new  Thread(new MyRunnable());
	  t.start();
	  Thread.sleep(1000);
	  // t.start();//�Ѿ���������������������,Ҫô��newһ�Σ�Ҫôʹ��threadpool
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
		System.out.println("��thread �߳�������");
	}
}