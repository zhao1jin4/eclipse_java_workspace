package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//Condition doc�ϵ�ʾ���� ArrayBlockingQueue��Դ��
class BoundedBuffer<E> {
   final Lock lock = new ReentrantLock(true);//true��ƽ��
   final Condition notFull  = lock.newCondition(); 
   final Condition notEmpty = lock.newCondition(); 

   final Object[] items = new Object[100];
   int putptr, takeptr, count;

   public void put(E x) throws InterruptedException {
     lock.lock();
     try {
       while (count == items.length)
         notFull.await();
       items[putptr] = x;
       if (++putptr == items.length) putptr = 0;
       ++count;
       notEmpty.signal();
     } finally {
       lock.unlock();
     }
   }

   public E take() throws InterruptedException {
     lock.lock();
     try {
       while (count == 0)
         notEmpty.await();//һ���߳�����waitʱ�������߳�(ͬһ����)���ǿ��Խ���ģ��������Ŀ��������˼
       E x = (E) items[takeptr];
       if (++takeptr == items.length) takeptr = 0;
       --count;
       notFull.signal();
       return x;
     } finally {
       lock.unlock();
     }
   }
   
   
  public static void main(String[] args) {
	  BoundedBuffer b=new BoundedBuffer();
	  new MyThread("one",b).start();
	  new MyThread("two",b).start();
  }
 }

class MyThread extends Thread
{
	  BoundedBuffer b;
	  public MyThread(String name,BoundedBuffer b)
	  {
		  super(name);
		  this.b=b;
	  }
	  @Override
	public void run()
	  {
		  try {
			b.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  }
}