package threadPool;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestThreadPool {
    private static int produceTaskSleepTime = 100;
    public static void main(String[] args) 
    {
    	ThreadGroup group=new ThreadGroup("my_group") {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println("thread group my_group catche Error");
				e.printStackTrace();
			}
    	};
    	ThreadFactory factory=new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				//可自己实现生成线程最大数,起名，放线程组
				return new Thread(group,r,"my_thread"+Math.random()*10+10);
			}
    	};
        //构造一个线程池
    	//如池中当线程数大于corePoolSize,如超出线程等待时间超过keepAliveTime会被终止
        ThreadPoolExecutor producerPool = new ThreadPoolExecutor(10, 20, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue(20),
        //        new ThreadPoolExecutor.DiscardOldestPolicy()
          //      new ThreadPoolExecutor.AbortPolicy()
                 factory
        );

        //每隔produceTaskSleepTime的时间向线程池派送一个任务。
        int i=1;
        while(true){
            try {
                Thread.sleep(produceTaskSleepTime);

                String task = "task@ " + i;
                System.out.println("put " + task);

                producerPool.execute(new ThreadPoolTask(task));

                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //producerPool.shutdown();
    }
}                   
