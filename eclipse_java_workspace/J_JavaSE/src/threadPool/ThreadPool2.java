package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreadPool2 {

	public static void main(String[] args) {   
        // 线程池     
        ExecutorService exec = Executors.newCachedThreadPool();//如创建的线程60秒未使用，则从cache中删  
        // 只能5个线程同时访问     
        final Semaphore semp = new Semaphore(5);     
        // 模拟20个客户端访问     
        for (int index = 0; index < 20; index++) {     
            final int NO = index;   
            Runnable run = new Runnable() {   
                public void run() {   
                    try {   
                        // 获取许可   
                        semp.acquire();   
                        System.out.println("thread:" + NO);   
                        Thread.sleep((long) (Math.random() * 1000));   
                        // 访问完后，释放   
                        semp.release();   
                    } catch (InterruptedException e) {   
                    }   
                }   
            };   
			exec.execute(run);     
        }     
        // 退出线程池     
        exec.shutdown();     
    }    
}