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
	    rtLock.writeLock().lock();  //锁升级，ReentrantReadWriteLock是不支持的。
	    System.out.println("blocking");
	}
	public static void lockDegrade( )   //ReentrantReadWriteLock支持锁降级
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
        