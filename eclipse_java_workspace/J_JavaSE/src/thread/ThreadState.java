package thread;

import java.util.concurrent.CountDownLatch;

public class ThreadState {
	public static void pringState(Thread a) {
		if (a.getState() == Thread.State.NEW)
			System.out.println("NEW");
		else if (a.getState() == Thread.State.RUNNABLE)
			System.out.println("RUNNABLE");
		else if (a.getState() == Thread.State.WAITING)
			System.out.println("WAITING");
		else if (a.getState() == Thread.State.TIMED_WAITING)
			System.out.println("TIMED_WAITING");
		else if (a.getState() == Thread.State.BLOCKED)
			System.out.println("BLOCKED");
		else if (a.getState() == Thread.State.TERMINATED)
			System.out.println("TERMINATED");
	}

	public static void main(String[] args) throws Exception {

		CountDownLatch latch = new CountDownLatch(2);

		MyThread t = new MyThread(latch,"t1");
		pringState(t); //NEW
		t.start();
		pringState(t);//RUNNABLE
		
		
		t.setSleepTime(2000);
		Thread.sleep(1000);
		pringState(t); //TIMED_WAITING

		
		System.out.println("----wait 1 second for  next loop:");
		Thread.sleep(2000);
		
		
		t.setBlockTime(5000);
		MyThread t2 = new MyThread(latch,"t2");
		t2.start();
		Thread.sleep(1000);
		System.out.print("main--t2--");
		pringState(t2);//BLOCKED
		t2.setStop(true);
		
		
		
		//wait报错
		for(int i=0;i<10;i++)
		{
			t.setNeverDead(true);
			Thread.sleep(1000);
			pringState(t);
		}
		
		
		//wait报错
//		t.setWaitTime(2000);
//		Thread.sleep(1000);
//		pringState(t);
//		t.notify();
		
		
		t.setStop(true);
		Thread.sleep(5000);
		pringState(t);//TERMINATED
		
		
		//System.out.print("start a dead thread Error ");
		//t2.start(); 
		
		
		latch.await();
	}
}

class MyThread extends Thread {
	private boolean stop = false;
	private boolean neverDead=false;
	private int sleepTime = 0;
	private int  waitTime=0;
	private int  blockTime=0;
	private CountDownLatch latch;

	public MyThread(CountDownLatch latch,String name) 
	{
		super(name);
		this.latch = latch;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	public int getWaitTime() {
		return waitTime;
	}

	public int getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(int blockTime) {
		this.blockTime = blockTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public boolean isNeverDead() {
		return neverDead;
	}

	public void setNeverDead(boolean neverDead) {
		this.neverDead = neverDead;
	}

	public void run() //不能声明抛异常，有可能RuntimeException
	{
		for(int i=0;i<100;i++)
		{
			
		}
		
		System.out.println(this.getName()+"--------first-------");
		while (!stop) 
		{
			if (sleepTime > 0) {
				System.out.println("sleep..");
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sleepTime = 0;
			}
			
			synchronized(MyThread.class)
			{
				System.out.println(this.getName()+"====synchronized");
				if(blockTime>0)
				{
					try {
						System.out.println(this.getName()+" blocking...");
						Thread.sleep(blockTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					blockTime=0;
					System.out.println(this.getName()+" blocking... done");
				}
			}
			
			
		
			if (neverDead) {
				try {
					this.join();//wait报错??????
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				neverDead=false;
			}
			
			
			if (waitTime > 0) {
				System.out.println("wait..");
				try {
					this.wait(waitTime);//wait报错
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				waitTime = 0;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//for
		
		System.out.println(this.getName()+" thread run done");
		latch.countDown();// 减一
	}//run

}