package thread;


public class SynchronizedTest
{
	
	
	//��static ,��һ��������m,���һ���߳̽���synchronized one ���� ��û�з���,����һ���߳�Ҳ���ܽ���synchronized two ���� , �뵱��synchronized(this)
	//��� static ���� ��synchronized,�൱�� synchronized(����),Ҳ�����һ���߳̽��� static synchronized one ����δ����,��һ���߳�static synchronized two ����������
	
	//����ο���one������һ���߳���ִ��,two����һ�̺߳�ִ��,����ʹ��Future ����Ҫ���̴߳�Future����
	
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
		//static int  count=0;//���ܶ�Ϊstatic 
		synchronized(lockObj)//ͬһ���߳̿��Զ�εõ�ͬһ��synchronized��(���ͷ���֮ǰ,��ݹ���� )
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
  