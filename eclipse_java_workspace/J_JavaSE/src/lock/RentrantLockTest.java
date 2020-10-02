package lock;

import java.util.concurrent.locks.ReentrantLock;

public class RentrantLockTest {
	public  	ReentrantLock reentrant=new ReentrantLock(true);//true��ƽ��
	public   void testRenentrant(int i)
	{ 
		/*
		try {
			//�����������������,����������߳��е���   ����̵߳Ķ���� .interupt()�����ж�����߳�,���׳� InterruptedException
			reentrant.lockInterruptibly();//lockInterruptibly()���Ա�thread.interupt()����������lock()�����Ա����
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		*/
		reentrant.lock();//����һ�μ�1
		reentrant.isLocked();
		reentrant.isHeldByCurrentThread();
		reentrant.getHoldCount();
//		Thread.holdsLock(obj);//��ǰ�߳� ����synchronize���������
//		Thread t;
//		t.isAlive();
//		t.getState();
		
		System.out.println(Thread.currentThread().getName()+"������ i="+i);
		 if(i-- > 0)
			 testRenentrant(i);   //��������ͷ�ǰ������̻߳��ɼ����õ���,synchronizeҲ�� 
		 reentrant.unlock();//�����������0�������̲߳��ܽ���
		 System.out.println(Thread.currentThread().getName()+"������ i="+i);
	}
	public static void main(String[] args) {
	  
		RentrantLockTest obj=new RentrantLockTest();
		Thread t1=	new Thread("�߳�1") {
			@Override
			public void run() {
				obj.testRenentrant(3);
			}
		};
	
		Thread t2=new Thread("�߳�2") {
			@Override
			public void run() {
				obj.testRenentrant(3);
			}
		};
		
		t1.start();
		t2.start();
	}

}
