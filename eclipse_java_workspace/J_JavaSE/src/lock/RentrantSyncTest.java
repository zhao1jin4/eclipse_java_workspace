package lock;

import java.util.concurrent.locks.ReentrantLock;

public class RentrantSyncTest {
	public static	ReentrantLock reentrant=new ReentrantLock();
	public static synchronized void testRenentrant(int i)  //synchronized��������ͷ�ǰ������̻߳��ɼ����õ���
	{
		System.out.println("������ i="+i);
		 if(i-- > 0)
			 testRenentrant(i);
		 System.out.println("������ i="+i);
	}
	public static void main(String[] args) {
	  
		testRenentrant(3);
	}

}
