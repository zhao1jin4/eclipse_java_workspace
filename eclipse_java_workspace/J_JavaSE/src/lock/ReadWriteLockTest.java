package lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest
{
	
	public static void lockUpgrade( ) 
	{
		
		ReadWriteLock rtLock = new ReentrantReadWriteLock();  
		rtLock.readLock().lock();  
	    System.out.println("get readLock.");  
	    rtLock.writeLock().lock();  //��������ReentrantReadWriteLock�ǲ�֧�ֵġ�
	    System.out.println("blocking");
	}
	public static void lockDegrade( )   //ReentrantReadWriteLock֧��������
	{
		ReadWriteLock rtLock = new ReentrantReadWriteLock();  
        rtLock.writeLock().lock();  
        System.out.println("writeLock");  
        rtLock.readLock().lock();  
        System.out.println("get read lock"); 
        rtLock.readLock().unlock();
        
	}

	public static void main(String[] args) {
		
		//lockUpgrade();
		lockDegrade();
	}
}
        