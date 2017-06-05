package util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhaojin
 */
public class ThreadUtil 
{
	private static String logContext="--线程工具--";
	private static final Logger logger= LoggerFactory.getLogger("service");

	private static ThreadGroup asyncGroup =new NamedThreadGroup ("asyncGroup");
	private static ArrayBlockingQueue<Thread> queue=new ArrayBlockingQueue<Thread>(200);
	private static int LatchCount=3;//同时3个线程执行
	private static CountDownLatch latch=new CountDownLatch(LatchCount);

	static
	{
		new Thread("take-blocking-queue-thread")
		{
			@Override
			public void run() 
			{
				int i=1;
				long count=latch.getCount();
				
				try 
				{
					while(true)
					{
						logger.info( logContext+"ready take blocking queue to run");
						Thread t = queue.take(); //pool 没有返回 null,阻塞
						t.start();
						logger.info(  logContext+"taked the thread {} and has bean started",t.getName());
						if(i++ % count + 1 ==0)
						{
							i=1;
							latch.await();
							latch=new CountDownLatch(LatchCount);
						}
					}
				} catch (InterruptedException e) 
				{
					logger.error(  logContext+"线程{}执行错误 ",this.getName(),e);
				}
			
			}
		}.start();
	}
	 
	//查Queue,BlockingQueue的API
	//Queue队列满时,add    方法抛出异常,offer(可传超时时间)方法返回 false   ,put 方法会阻塞	,Insert操作
	//Queue队列空时,remove 方法抛出异常 ,poll(可传超时时间)方法返回 null	,take方法会阻塞	, Remove操作
	//Queue队列空时,element方法抛出异常 ,peek 			   方法返回 null   					,examine操作
	public  static  void submitAsyncTask(NamedRunnable task)
	{
		 Thread t=new Thread(asyncGroup,task,task.getName());
		boolean ok= queue.offer(t);//满时 offer(可传超时时间)方法返回 false 
		if(!ok)
		{
			try{
				logger.warn( logContext+"线程队列数 {} 已经满， 开始同步执行线程{}",queue.size(),t.getName());
				 t.run();
			}catch(Exception ex)
			{
				logger.error( logContext+"在父线程中同步执行子线程{} 错误",task.getName(),ex);
			}
		}
		
	}

	
	private static ThreadPoolExecutor  threadPool = new ThreadPoolExecutor(2,3, 10,TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(2), new ThreadPoolExecutor.CallerRunsPolicy());//如果队列满,新加入的使用当前线程调用run而不是start运行,相当于未开线程
	public  static <T>  Future<T> submitAsyncTaskWithResult(BaseCallable<T> task)
	{
		return threadPool.submit(task);
	}
	
	
	
	//----------------------------测试区
	 // TestSyncThread
	public static void main(String[] args) {
		for(int i=0;i<30;i++)
		{
			//ThreadUtil.submitAsyncTaskWithResult( new MyBaseCallable<String>(i) );
			
			ThreadUtil.submitAsyncTask(new MyNamedRunnable("t-"+i , i) );
		}
	}
	
}

//----------------------------测试区
class MyNamedRunnable extends  NamedRunnable  
{
	//匿名内部类不能使用static变量，也不能带构造器
	public int val=0;
		
	public MyNamedRunnable(String name,int value)
	{
		super(name);
		val=value;
	}
	
	@Override
	public void run() {
		System.out.println(val++);
		if(val%3==0)
			throw new RuntimeException("thread 错误"+val);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}} 
class MyBaseCallable<T> extends BaseCallable<T>
{
	 int val;
	public MyBaseCallable(int val) {
		super();
		this.val = val;
	}

	@Override
	public T doCall() {
		
		System.out.println(val);
		if(val%3==0)
			throw new RuntimeException("thread 错误"+val);
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
