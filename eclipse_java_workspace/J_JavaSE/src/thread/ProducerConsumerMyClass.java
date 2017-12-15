package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

 public class ProducerConsumerMyClass 
  {
	class MyProducer extends Thread
	{
		char data='A';
		BlockingQueue<Object> queue;
		public MyProducer(String name) {
			super(name);
		}
		 
		public void setQueue(BlockingQueue<Object> queue)
		{
			this.queue = queue;
		}

		public void run() 
		{
			while(data<'Z')
			{	
				data=(char)((int)data + 1);
				try
				{
					queue.put(data);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println("����"+data);
				 
			}
		}
	}
	class Consumer extends Thread
	{
		BlockingQueue<Object> queue;
		public Consumer(String name) {
			super(name);
		}
		public void setQueue(BlockingQueue<Object> queue)
		{
			this.queue = queue;
		}

		public void run() 
		{
			while(true)
			{
				try{
					Object o=queue.take();
					System.out.println("����"+o);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	public static void main(String[] args) 
	{
		BlockingQueue<Object> queue=new ArrayBlockingQueue<Object>(1);
		
		ProducerConsumerMyClass out=new ProducerConsumerMyClass();
		MyProducer p=out.new MyProducer("Producer");
		p.setQueue(queue);
		
		Consumer c=out.new Consumer("Consumer");
		c.setQueue(queue);
		
		p.start();
		c.start();
		
		
		//������ �� ������  ����ʹ��PipedInputStream�� ���ҵ�ʾ��  PipedStreamTest
	}
}
