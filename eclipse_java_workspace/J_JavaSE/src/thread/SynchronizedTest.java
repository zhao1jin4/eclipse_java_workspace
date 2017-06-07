package thread;


public class SynchronizedTest
{
	
	
	//非static ,在一个对象中m,如果一个线程进入synchronized one 方法 还没有返回,则另一个线程也不能进入synchronized two 方法 , 想当于synchronized(this)
	//如果 static 方法 加synchronized,相当于 synchronized(类名),也是如果一个线程进入 static synchronized one 方法未返回,另一个线程static synchronized two 方法会阻塞
	
	//但如何控制one方法在一个线程先执行,two在另一线程后执行,可以使用Future 可能要主线程传Future对象
	
	public synchronized  void one()
	{
		System.out.println(Thread.currentThread().getName()+"_in one");
	}
	public synchronized void two()
	{
		System.out.println(Thread.currentThread().getName()+"_in two");
	}
	
	public synchronized  static void oneStatic()
	{
		System.out.println(Thread.currentThread().getName()+"_in static one");
	}
	public synchronized static void twoStatic()
	{
		System.out.println(Thread.currentThread().getName()+"_in static two");
	}
	private static Object lockObj=new Object();
	static int  count=0; 
	public   static void recursveSync()
	{
		//static int  count=0;//不能定为static 
		synchronized(lockObj)//同一个线程可以多次得到同一个synchronized锁(在释放锁之前,如递归调用 )
		{
			if(count >8)
				return;
			System.out.println("recursveSync before" + count++);
			 recursveSync();
			System.out.println("recursveSync after" + count--);
		}
	}
	
	
  public static void main(String[] args) 
  {
	  recursveSync();
	  
	 final SynchronizedTest test=new SynchronizedTest();
	 
	 new Thread("my-1")
		{
			@Override
			public void run() {
				SynchronizedTest.oneStatic();
				
				test.one();
				test.two();
			}
		}.start();
		
		new Thread("my-2")
		{
			@Override
			public void run() {
				
				SynchronizedTest.twoStatic();
				
				test.two();
				test.one();
			}
		}.start();
		
		
		LoggingWidget x=new LoggingWidget();
		x.doSomething();
  }
}

class Widget
{
	public synchronized void doSomething()
	{
		System.out.println("int Widget  doSomething" );
	}
}
class LoggingWidget extends Widget
{
	public synchronized void doSomething()
	{
		System.out.println("int LoggingWidget  doSomething" );
		super.doSomething();
	}
}
  