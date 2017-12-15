package threadPool;

import java.io.Serializable;

/**
 * �̳߳�ִ�е�����
 * @author hdpan
 */
public class ThreadPoolTask implements Runnable,Serializable{
 
    //JDK1.5�У�ÿ��ʵ��Serializable�ӿڵ��඼�Ƽ�����������һ��ID
    private static final long serialVersionUID = 0;

    private static int consumeTaskSleepTime = 2000;
    private Object threadPoolTaskData;
   
    ThreadPoolTask(Object tasks){
        this.threadPoolTaskData = tasks;
    }
 
    //ÿ�������ִ�й��̣�������ʲô��û��������print��sleep��:)
    public void run(){
        System.out.println("start .."+threadPoolTaskData);
        try {
            //���ڹ۲����󣬵ȴ�һ��ʱ��
            Thread.sleep(consumeTaskSleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        threadPoolTaskData = null;
    }
}               
   