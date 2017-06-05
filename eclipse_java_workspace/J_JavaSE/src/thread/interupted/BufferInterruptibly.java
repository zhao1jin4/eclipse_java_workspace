package thread.interupted;
import java.util.concurrent.locks.ReentrantLock;      
       
public class BufferInterruptibly {      
       
    private ReentrantLock lock = new ReentrantLock();      
       
    public void write() {      
        lock.lock();      
        try {      
            long startTime = System.currentTimeMillis();      
            System.out.println("��ʼ�����buffд�����ݡ�");      
            for (;;)// ģ��Ҫ����ܳ�ʱ��      
            {      
                if (System.currentTimeMillis()      
                        - startTime > Integer.MAX_VALUE)      
                    break;      
            }      
            System.out.println("����д����");      
        } finally {      
            lock.unlock();      
        }      
    }      
       
    public void read() throws InterruptedException {      
        //lock.lockInterruptibly();// ע�����������Ӧ�ж�      
    	lock.lock();
        try {      
            System.out.println("�����buff������");      
        } finally {      
            lock.unlock();      
        }      
    }      
       
}    