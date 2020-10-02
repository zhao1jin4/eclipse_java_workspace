package quiz;

import java.util.concurrent.CountDownLatch;

public class DeadLock {
	public static void main(String[] args) throws Exception {
		Object lockA=new Object();
		Object lockB=new Object();
		CountDownLatch latch=new CountDownLatch(2);
		new Thread() {
			public void run()
			{
				synchronized(lockA)
				{
					try {
						Thread.sleep(1000);
					} catch ( Exception e) { e.printStackTrace(); }
					synchronized(lockB)
					{
						
					}
				}
				latch.countDown();
				System.out.println("Thread1 end");
			}
			
		}.start();

		new Thread() {
			public void run()
			{
				synchronized(lockB)
				{
					try {
						Thread.sleep(1000);
					} catch ( Exception e) { e.printStackTrace(); }
					synchronized(lockA)
					{
						
					}
				}
				latch.countDown();
				System.out.println("Thread2 end");
			}
		}.start();
		 latch.await();
	}
}
