package thread.advance;

import java.util.concurrent.CountDownLatch;

public class ThreadLocalTest {
	public static ThreadLocal<String> local=new ThreadLocal<String>();//����Ϊstatic ,ֻ��set,get,remove,�൱��һ��Map,keyΪ��ǰ�̵߳�ID,�ж��ٸ��߳�������,Map����ж���

	
	
	public static void main(String[] args)
	{
		final	CountDownLatch countDownLatch = new CountDownLatch(2);
			
		new Thread(new Runnable()
			{
				public void run() 
				{
					for (int i=0;i<10;i++)
					{
						ThreadLocalTest.local.set("��"+i);
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
						ThreadLocalTest.local.set("��"+i);
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
		
		System.out.println("���̵߳����߳�ִ��");
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
