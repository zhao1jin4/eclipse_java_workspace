package quiz;

public class ThreadLockObject {

	public void  concurrentMethod (boolean isFirst)  
	{	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String key="first";
		if(!isFirst)
		{
			key="last";
		}
		synchronized (key) {
			System.out.println(key);
		}
		
		
	}
	public static void main(String[] args) {
		 
		ThreadLockObject t=new ThreadLockObject();
		
		new Thread(){
			@Override
			public void run() {
				t.concurrentMethod(true);
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				t.concurrentMethod(false);
			}
		}.start();
		
	}

}
