package thread.advance;


class MyThreadGroup extends ThreadGroup
{
	public MyThreadGroup(String name) {
		super(name);
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {//异常捕获OK,  2
		//super.uncaughtException(t, e);
		System.err.print("MyThreadGroup---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());
	}
}
//使用 util.thread中的类
public class NamedThreadCatche {
	public static void main(String[] args) {
		
		//异常捕获 ,优级高到低为 thread.setUncaughtExceptionHandler ->  ThreadGroup -> Thread.setDefaultUncaughtExceptionHandler(全局)
		MyThreadGroup group =new MyThreadGroup("myThreadGroup");
//		group.destroy();
//		group.interrupt();//中止线程
		Thread thread=new Thread(group, new Runnable(){
			@Override
			public void run() {
				System.out.print("在子线程中执行");
				throw new RuntimeException("have exception");
			}
			
		},"myThread-01");//线程带名字OK
		
		
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { //异常捕获OK,1
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.print("this setUncaughtExceptionHandler---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());				
			}
		} );
		
		thread.start();
		
		
	
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {//全局异常捕获OK,3
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.print("global setUncaughtExceptionHandler---catch error in thread:"+t.getName()  +" have error :"+e.getMessage());
			}
		});
		/*	Thread thread2=new Thread(  new Runnable(){
			@Override
			public void run() {
				System.out.print("在子线程中执行");
				throw new RuntimeException("thread2 have exception");
			}
			
		});
		
		thread2.start();
		*/
		
	}

}
