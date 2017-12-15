package threadPool;

import java.io.Serializable;

/**
 * 线程池执行的任务
 * @author hdpan
 */
public class ThreadPoolTask implements Runnable,Serializable{
 
    //JDK1.5中，每个实现Serializable接口的类都推荐声明这样的一个ID
    private static final long serialVersionUID = 0;

    private static int consumeTaskSleepTime = 2000;
    private Object threadPoolTaskData;
   
    ThreadPoolTask(Object tasks){
        this.threadPoolTaskData = tasks;
    }
 
    //每个任务的执行过程，现在是什么都没做，除了print和sleep，:)
    public void run(){
        System.out.println("start .."+threadPoolTaskData);
        try {
            //便于观察现象，等待一段时间
            Thread.sleep(consumeTaskSleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        threadPoolTaskData = null;
    }
}               
   