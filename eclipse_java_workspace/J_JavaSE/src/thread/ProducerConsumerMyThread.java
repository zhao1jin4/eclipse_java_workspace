package thread;


 public class ProducerConsumerMyThread 
  { 
	
	class MyProducer extends Thread
	{
		boolean isHave=false;
		char data='A';
		Consumer consumer;
		
		public MyProducer(String name) {
			super(name);
		}

		public void setData(char data) {
			this.data = data;
		}
		
		public char getData() {
			return data;
		}

		public void setConsumer(Consumer consumer) {
			this.consumer = consumer;
		}
		
		public void run() 
		{
			while(data<'Z')
			{	
				try
				{
					synchronized(consumer)//得到对像的Monitor,synchronized来得到，才可以调wait ,notify,　　
					//jconosle可以看到调用堆栈方法 卡到wait,jvisualvm 可以到线程等待多长时间,,,,如是死锁，死偱环如何知道
					{
						data=(char)((int)data + 1);
						setData(data);
						System.out.println("生产"+getData());
						
						isHave=true;
						consumer.notify();
					}
					synchronized(this)
					{
						while(isHave)
							this.wait();///官方文档上说,有时可能是spurious(假的) wakeup,要放在一个while循环中
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Consumer extends Thread
	{
		public Consumer(String name) {
			super(name);
		}
		MyProducer producer;
		public void setProducer(MyProducer producer) {
			this.producer = producer;
		}
		public void run() 
		{
			while(true)
			{
				try{
					synchronized(this)
					{
						while(! producer.isHave)
							this.wait();///官方文档上说,有时可能是spurious(假的) wakeup,要放在一个while循环中
					}
					synchronized(producer)
					{
						System.out.println("消费"+producer.getData());
						producer.isHave=false;
						producer.notify();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	public static void main(String[] args) 
	{
		ProducerConsumerMyThread out=new ProducerConsumerMyThread();
		MyProducer p=out.new MyProducer("Producer");
		Consumer c=out.new Consumer("Consumer");
		p.setConsumer(c);
		c.setProducer(p);
		
		p.start();
		c.start();
		
	}
}
