package threadPool;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.TimeUnit;  
import java.util.concurrent.locks.Condition;  
import java.util.concurrent.locks.Lock;  
import java.util.concurrent.locks.ReentrantLock;  

class Car2 
{   
	//Reentrant再进去re entrant
    private Lock lock = new ReentrantLock(true);  
    //默认是false 不公平,如为true 选择等待时间最长的线程进入
    //前一个线程进入lock()还没有退出unlock(),后一个线程不可以进入lock(),除非前一个线程进入.newCondition().await时,后一个线程才可进入
    private Condition condition = lock.newCondition();  
    private boolean waxOn = false;  
    public void waxed() {  
         lock.lock(); 
        try {  
            waxOn = true;  
            condition.signalAll();  //必须先得到lock()
        } finally {  
           lock.unlock();  
        }  
    }  
    public void buffed() {  
        lock.lock();  
        try {  
            waxOn = false;  
            condition.signalAll();  
        } finally {  
            lock.unlock();  
        }  
    }  
    public void waitForWaxing() throws InterruptedException {  
        lock.lock();  
        try {  
            while (waxOn == false) {  
                condition.await();  //必须先得到lock()
            }  
        } finally {  
            lock.unlock();  
        }  
    }  
    public void waitForBuffing() throws InterruptedException {  
        lock.lock();  
        try {  
            while (waxOn == true) {  
                condition.await();  
            }  
        } finally {  
            lock.unlock();  
        }  
    }  
}  
class WaxOn2 implements Runnable {  
    private Car2 car;  
    public WaxOn2(Car2 car) {  
        this.car = car;  
    }  
    public void run() {  
        try {  
            while (!Thread.interrupted()) {  
                System.out.println("Wax On!");  
                TimeUnit.MILLISECONDS.sleep(200);  
                car.waxed();  
                car.waitForBuffing();  
            }  
        } catch (InterruptedException e) {  
            System.out.println("Exiting via interrupt");  
        }  
        System.out.println("Ending Wax On task");  
    }  
}  
class WaxOff2 implements Runnable {  
    private Car2 car;  
    public WaxOff2(Car2 car) {  
        this.car = car;  
    }  
    public void run() {  
        try {  
            while (!Thread.interrupted())//是否超时 　exec.shutdownNow();  
            {  
                car.waitForWaxing();  
                System.out.println("Wax Off");  
                TimeUnit.MILLISECONDS.sleep(200);  
                car.buffed();  
            }  
        } catch (InterruptedException e) {  
            System.out.println("Exiting via interrupt");  
        }  
        System.out.println("Ending Wax Off task");  
    }  
}  
public class RentrantLockMain 
{  
    public static void main(String[] args) throws InterruptedException {  
        Car2 car = new Car2();  
        ExecutorService exec = Executors.newCachedThreadPool();  
        exec.execute(new WaxOn2(car)); //一辆车不可同时上蜡和去蜡 
        exec.execute(new WaxOff2(car));  
        TimeUnit.SECONDS.sleep(50);  
        exec.shutdownNow();  
    }  
}  