package thread.advance;

import java.util.concurrent.CountDownLatch;

public class ThreadLocalTest {
	public static ThreadLocal<String> local=new ThreadLocal<String>();//声明为static ,只有set,get,remove,相当于一个Map,key为当前线程的ID,有多少个线程在运行,Map里就有多少

	
	
	public static void main(String[] args)
	{
		final	CountDownLatch countDownLatch = new CountDownLatch(2);
			
		new Thread(new Runnable()
			{
				public void run() 
				{
					for (int i=0;i<10;i++)
					{
						ThreadLocalTest.local.set("李"+i);
						synchronized (this) {
							try {
								this.wait(20);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println(Thread.currentThread().getName()+":"+ThreadLocalTest.local.get());
					}
					
					countDownLatch.countDown();
				}
			},"Li").start();
			
		new Thread(new Runnable()
			{
				public void run() 
				{	
					for (int i=0;i<10;i++)
					{
						ThreadLocalTest.local.set("王"+i);
						synchronized (this) {
							try {
								this.wait(20);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println(Thread.currentThread().getName()+":"+ThreadLocalTest.local.get());
					}
					
					countDownLatch.countDown();
				}
				
			},"Wang").start();
		
		System.out.println("主线程等子线程执行");
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
