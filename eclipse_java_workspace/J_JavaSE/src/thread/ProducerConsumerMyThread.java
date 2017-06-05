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
					synchronized(consumer)//�õ������Monitor,synchronized���õ����ſ��Ե�wait ,notify,����
					//jconosle���Կ������ö�ջ���� ����wait,jvisualvm ���Ե��̵߳ȴ��೤ʱ��,,,,�������������Ż����֪��
					{
						data=(char)((int)data + 1);
						setData(data);
						System.out.println("����"+getData());
						
						isHave=true;
						consumer.notify();
					}
					synchronized(this)
					{
						while(isHave)
							this.wait();///�ٷ��ĵ���˵,��ʱ������spurious(�ٵ�) wakeup,Ҫ����һ��whileѭ����
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
							this.wait();///�ٷ��ĵ���˵,��ʱ������spurious(�ٵ�) wakeup,Ҫ����һ��whileѭ����
					}
					synchronized(producer)
					{
						System.out.println("����"+producer.getData());
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
