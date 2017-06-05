package thread.advance;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangeTest {

	Exchanger<String> exchanger=new Exchanger<String> ();
	//�����߳�ͬʱ����ִ�е�exchange����ʱ��������,��һ���߳��ȵ�,�ȴ�
	public static void main(String[] args) 
	{
		ExchangeTest test =new ExchangeTest();
		ExecutorService service=Executors.newCachedThreadPool();
		MyRunnable2 r2=test.new MyRunnable2("r2");
		MyRunnable1 r1=test.new MyRunnable1("r1");
		
	
		{
			int a=0;
			a++;
		}
		service.execute(r2);
		service.execute(r1);
		
		service.shutdown();
	}
	class MyRunnable1 implements Runnable 
	{
		
		private String name;
		public MyRunnable1(String name)
		{
			this.name=name;
		}
		//���������������췽��,���ڹ��췽��ǰ������ ,�ڷ�����ֻ��һ������
		{
			int a=0;
			a++;
		}
		public void run() 
		{
			try {
				
				
				String data1="����1";
				Thread.sleep((long)(Math.random()*1000));
				{
					int a=0;
					a++;
				}
				String receiveData=exchanger.exchange(data1);
				System.out.println(name+"�����������:"+receiveData);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}  
			
		}
	}
	class MyRunnable2 implements Runnable 
	{
		private String name;
		public MyRunnable2(String name)
		{
			this.name=name;
		}
		CyclicBarrier cyclic =new CyclicBarrier(3);
		public void run() 
		{
			try {
				
				String data2="����2";
				Thread.sleep((long)(Math.random()*1000));
				String receiveData=exchanger.exchange(data2);
				System.out.println(name+"�����������:"+receiveData);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}  
			
		}
	}

}
