package thread.advance;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

	public static void main(String[] args) 
	{
		CyclicBarrierTest test =new CyclicBarrierTest();
		ExecutorService service=Executors.newCachedThreadPool();
		MyRunnable r=test.new MyRunnable();
		for(int i=0;i<3;i++)
		{
			service.execute(r);
		}
		service.shutdown();
	}
	class MyRunnable implements Runnable 
	{
		CyclicBarrier cyclic =new CyclicBarrier(3);//3���̵߳��ý����,���ǲſ�һ�����ִ��,
		public void run() 
		{
			try {
				
//				Random random=new Random();
//				int randrom=random.nextInt();
//				randrom=random.nextInt();
				
				Thread.sleep((long)(Math.random()*1000));
				System.out.println(Thread.currentThread().getName()+":11�ȴ�����"+ (cyclic.getNumberWaiting()+1 ));
				cyclic.await();//3���̵߳��ý����,���ǲſ�һ�����ִ��,
				
				Thread.sleep((long)(Math.random()*10000));
				System.out.println(Thread.currentThread().getName()+":22�ȴ�����"+  (cyclic.getNumberWaiting()+1));
				cyclic.await();//3���̵߳��ý����,���ǲſ�һ�����ִ��,
				
			} catch ( Exception e) {
				e.printStackTrace();
			}  
			
		}
	}
}
