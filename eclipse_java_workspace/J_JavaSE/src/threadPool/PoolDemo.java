package threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class PausableThreadPoolExecutor extends ThreadPoolExecutor {
	   private boolean isPaused;
	   private ReentrantLock pauseLock = new ReentrantLock();
	   private Condition unpaused = pauseLock.newCondition();
	   //FutureTask

	   public PausableThreadPoolExecutor() 
	   {
		   	super(10,10,1,TimeUnit.MILLISECONDS,new ArrayBlockingQueue(3)); 
		   	//(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)

	   }

	   protected void beforeExecute(Thread t, Runnable r) {
	     pauseLock.lock();
	     try {
	       while (isPaused) 
	    	   	unpaused.await(); //等待
	     } catch (InterruptedException ie) {
	       t.interrupt();
	     } finally {
	       pauseLock.unlock();
	     }
	   }

	   public void pause() {
	     pauseLock.lock();
	     try {
	       isPaused = true;
	     } finally {
	       pauseLock.unlock();
	     }
	   }

	   public void resume() {
	     pauseLock.lock();
	     try {
	       isPaused = false;
	       unpaused.signalAll();//不再等待
	     } finally {
	       pauseLock.unlock();
	     }
	   }
	 }
	 