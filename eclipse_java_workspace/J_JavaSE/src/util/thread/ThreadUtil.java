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
	private static String logContext="--�̹߳���--";
	private static final Logger logger= LoggerFactory.getLogger("service");

	private static ThreadGroup asyncGroup =new NamedThreadGroup ("asyncGroup");
	private static ArrayBlockingQueue<Thread> queue=new ArrayBlockingQueue<Thread>(200);
	private static int LatchCount=3;//ͬʱ3���߳�ִ��
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
						Thread t = queue.take(); //pool û�з��� null,����
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
					logger.error(  logContext+"�߳�{}ִ�д��� ",this.getName(),e);
				}
			
			}
		}.start();
	}
	 
	//��Queue,BlockingQueue��API
	//Queue������ʱ,add    �����׳��쳣,offer(�ɴ���ʱʱ��)�������� false   ,put ����������	,Insert����
	//Queue���п�ʱ,remove �����׳��쳣 ,poll(�ɴ���ʱʱ��)�������� null	,take����������	, Remove����
	//Queue���п�ʱ,element�����׳��쳣 ,peek 			   �������� null   					,examine����
	public  static  void submitAsyncTask(NamedRunnable task)
	{
		 Thread t=new Thread(asyncGroup,task,task.getName());
		boolean ok= queue.offer(t);//��ʱ offer(�ɴ���ʱʱ��)�������� false 
		if(!ok)
		{
			try{
				logger.warn( logContext+"�̶߳����� {} �Ѿ����� ��ʼͬ��ִ���߳�{}",queue.size(),t.getName());
				 t.run();
			}catch(Exception ex)
			{
				logger.error( logContext+"�ڸ��߳���ͬ��ִ�����߳�{} ����",task.getName(),ex);
			}
		}
		
	}

	
	private static ThreadPoolExecutor  threadPool = new ThreadPoolExecutor(2,3, 10,TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(2), new ThreadPoolExecutor.CallerRunsPolicy());//���������,�¼����ʹ�õ�ǰ�̵߳���run������start����,�൱��δ���߳�
	public  static <T>  Future<T> submitAsyncTaskWithResult(BaseCallable<T> task)
	{
		return threadPool.submit(task);
	}
	
	
	
	//----------------------------������
	 // TestSyncThread
	public static void main(String[] args) {
		for(int i=0;i<30;i++)
		{
			//ThreadUtil.submitAsyncTaskWithResult( new MyBaseCallable<String>(i) );
			
			ThreadUtil.submitAsyncTask(new MyNamedRunnable("t-"+i , i) );
		}
	}
	
}

//----------------------------������
class MyNamedRunnable extends  NamedRunnable  
{
	//�����ڲ��಻��ʹ��static������Ҳ���ܴ�������
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
			throw new RuntimeException("thread ����"+val);
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
			throw new RuntimeException("thread ����"+val);
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
