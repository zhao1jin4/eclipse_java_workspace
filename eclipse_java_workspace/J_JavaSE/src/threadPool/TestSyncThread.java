package threadPool;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import org.junit.Test; //junit 4 
import org.junit.jupiter.api.Test; //junit 5
import static org.junit.jupiter.api.Assertions.*;//junit 5

public class TestSyncThread   
{
	/*
	//没用???? 
	class MyThreadPoolExecutor extends ThreadPoolExecutor
	{
		public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue,
				RejectedExecutionHandler handler) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		}

		@Override
		protected void beforeExecute(Thread thread, Runnable r) {
			//super.beforeExecute(thread, r);
			thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {  
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.err.print("beforeExecute---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());				
				}
			} );
			
		}
		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			//super.afterExecute(r, t);
			 
			  if (t == null && r instanceof Future<?>) {
		       try {
		         Object result = ((Future<?>) r).get();
		       } catch (CancellationException ce) {
		           t = ce;
		       } catch (ExecutionException ee) {
		           t = ee.getCause();
		       } catch (InterruptedException ie) {
		           Thread.currentThread().interrupt(); // ignore/reset
		       }
		     }
     
			if(t!=null)
				System.err.print("afterExecute---catch error  have error :"+t.getMessage());
		}
		
	}

	//没用??
	class MyThreadGroup extends ThreadGroup
	{
		public MyThreadGroup(String name) {
			super(name);
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) { 
			//super.uncaughtException(t, e);
			System.err.print("MyThreadGroup---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());
		}
	}
 */
	//使用 ThreadUtil类
	 //如一个有异常,有时会影响其它线程的???,如果两个都有异常,有时会只有报一个异常的错误,只能在每个任务中try???
	//ThreadPoolExecutor 中用 ThreadGroup 捕没异常没用的???,thread.setUncaughtExceptionHandler没用的  ???, 如何捕??
	
	//线程池名字没用,因为线程会不停的重建
	private   ThreadPoolExecutor  waitingExecutor = new   ThreadPoolExecutor(2,3, 10,TimeUnit.SECONDS,
						new ArrayBlockingQueue<Runnable>(30), new ThreadPoolExecutor.AbortPolicy());
	private ExecutorCompletionService<Object>  waitingComplete =new ExecutorCompletionService<Object>(waitingExecutor);
	private int tasks=0;
	//private MyThreadGroup group=new MyThreadGroup("group00001");
	public  void  submitWaitingTask(Runnable task)//要求父线程退出前,等待子线程运行完成 ,Runnable无法得到结果
	{
		 tasks++;
		 Future<Object> f =  waitingComplete.submit(task,null);
		// Future<Object> f =  waitingComplete.submit(new Thread(group,task),null);
	}
	public  Future<Object> submitWaitingTask(Callable<Object> task)//要求父线程退出前,等待子线程运行完成,如要结果使用 Callable
	{
		 tasks++;
		  Future<Object> f= waitingComplete.submit(task);
		  return f;
	}
	public   void waitingThread()  
	{
		try {
			for (int i = 0; i < tasks; ++i) {
				Future<Object> futrue=waitingComplete.take();
				Object obj=futrue.get();//可以得到执行结果
			}
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		waitingExecutor.shutdown();
		
		
		//waitingExecutor.getTaskCount();
//		while(waitingExecutor.getActiveCount()!=0)
//		{
//			System.out.println("waitingThread  等线程中");
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		//--------必须调用shutdown,才会是true
//		if(!waitingExecutor.isTerminated() ) 
//		{
//			try {
//				waitingExecutor.awaitTermination(5, TimeUnit.SECONDS);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
	}
	//--------------
	@Test   
	public void testSyncThread () throws Exception 
	{  
		
		System.out.println("主线程开始");
	
 	submitWaitingTask(new Runnable()
		{
			@Override
			public void run() {
				System.out.println("Runnable子线程开始");
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					System.err.println("sleep----error");
				}
				System.out.println("Runnable子线程End");
  		    throw new RuntimeException("Runnable 的运行异常");
			}
		});

		

	Future callFuture =	 submitWaitingTask(new Callable<Object>()
		{
			@Override
			public Object call() throws Exception {
				System.out.println("Callable子线程开始");
				try {
					Thread.sleep(1*1000);
				} catch (InterruptedException e) {
					System.err.println("sleep----error");
				}
				
//				if(1 == 1)
//					throw new  Exception("Callable 的 异常");
				
				System.out.println("Callable子线程End");
				return new String("Callable 线程结果");
			}
		});
		
		System.out.println("主线程--执行OK,等待...");
		waitingThread();
		System.out.println("call result="+callFuture.get());
		
		
		Thread.sleep(60*1000);//为了打子线程的日志
		System.out.println("主线程-退出前");
		
	}
}
