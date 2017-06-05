package thread;



import java.util.*;
import java.io.*;

// We would like to synchronize producer and consumer so that
// producer puts a number in the buffer, then the consumer takes it
// out, then the producer puts another number, and so on.

// This solution provides the right behaviour
// We have changed the class Buffer to include wait() and notify()
// We also have changed the producer and cinsumer classes
// slightly to handle a new exception

public class ProducerConsumerGood 
{

  public static void main (String [] args) 
  {
    Buffer buf = new Buffer();
    
    // create new threads
    Thread prod = new Producer(10, buf);
    Thread cons = new Consumer(10, buf);
    
    // starting threads
    prod.start();
    cons.start();
    
    // Wait for the threads to finish
    try {
    	prod.join();
    	cons.join();
    } catch (InterruptedException e) {return;}
  }
 
} 

class Buffer {
  		private int contents;
  		private boolean empty = true;
  		
  		//两个方法的synchronized都是this,也就是一个方法未执行完,不能执行第二个
  		//当一个put/get 连续执行两次就会wait
  		public synchronized void put (int i) throws InterruptedException { 
  			while (empty == false) {// 官方文档上说,有时可能是spurious(假的) wakeup,要放在一个while循环中
  				try { wait(); }
  				catch (InterruptedException e) {throw e;}
  			}
  			contents = i;
  			System.out.println("Producer: put..." + i);
  			empty = false;//修改放在wait后,notify能防止死锁
  			notify();
  		} 
  		
  		public synchronized int get () throws InterruptedException {
  			while (empty == true)  {	//wait till something appears in the buffer
  				try { 	wait(); }
  				catch (InterruptedException e) {throw e;}
  			}
  			int val = contents;
  			System.out.println("Consumer: got..." + val);
  			empty = true;
  			notify();
  			return val;
  		}
}


 class Producer extends Thread {
  	private int n;
  	private Buffer prodBuf;
  	
  	public Producer (int m, Buffer buf) {
  		n = m;
  		prodBuf = buf;
    }
    
    public void run() {
    	for (int i = 0; i < n; i++) {
    	    try {
    	    	prodBuf.put(i + 1); //starting from 1, not 0
    	    } catch (InterruptedException e) {return;}
    		
    	}
    }
  }
 class Consumer extends Thread {
  	private int n;
  	private Buffer consBuf;
  	
  	public Consumer (int m, Buffer buf) {
  		n = m;
  		consBuf = buf;
    }
    
    public void run() {
    	int value;
    	for (int i = 0; i < n; i++) {
    		try {
    			value = consBuf.get();
    		}  catch (InterruptedException e) {return;}
    		
    	}
    }
  }