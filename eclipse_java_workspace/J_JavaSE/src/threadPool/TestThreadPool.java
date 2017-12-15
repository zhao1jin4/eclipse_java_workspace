package threadPool;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
    private static int produceTaskSleepTime = 100;
    public static void main(String[] args) 
    {
        //����һ���̳߳�
    	//����е��߳�������corePoolSize,�糬���̵߳ȴ�ʱ�䳬��keepAliveTime�ᱻ��ֹ
        ThreadPoolExecutor producerPool = new ThreadPoolExecutor(10, 20, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue(20),
        //        new ThreadPoolExecutor.DiscardOldestPolicy()
                new ThreadPoolExecutor.AbortPolicy()
        );

        //ÿ��produceTaskSleepTime��ʱ�����̳߳�����һ������
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
