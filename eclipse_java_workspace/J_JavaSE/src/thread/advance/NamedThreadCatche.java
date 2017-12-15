package thread.advance;


class MyThreadGroup extends ThreadGroup
{
	public MyThreadGroup(String name) {
		super(name);
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {//�쳣����OK,  2
		//super.uncaughtException(t, e);
		System.err.print("MyThreadGroup---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());
	}
}
//ʹ�� util.thread�е���
public class NamedThreadCatche {
	public static void main(String[] args) {
		
		//�쳣���� ,�ż��ߵ���Ϊ thread.setUncaughtExceptionHandler ->  ThreadGroup -> Thread.setDefaultUncaughtExceptionHandler(ȫ��)
		MyThreadGroup group =new MyThreadGroup("myThreadGroup");
//		group.destroy();
//		group.interrupt();//��ֹ�߳�
		Thread thread=new Thread(group, new Runnable(){
			@Override
			public void run() {
				System.out.print("�����߳���ִ��");
				throw new RuntimeException("have exception");
			}
			
		},"myThread-01");//�̴߳�����OK
		
		
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { //�쳣����OK,1
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.print("this setUncaughtExceptionHandler---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());				
			}
		} );
		
		thread.start();
		
		
	
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {//ȫ���쳣����OK,3
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.print("global setUncaughtExceptionHandler---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());
			}
		});
		/*	Thread thread2=new Thread(  new Runnable(){
			@Override
			public void run() {
				System.out.print("�����߳���ִ��");
				throw new RuntimeException("thread2 have exception");
			}
			
		});
		
		thread2.start();
		*/
		
	}

}
