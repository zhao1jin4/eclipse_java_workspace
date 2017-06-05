package threadPool.fork_join;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
  
class Fibonacci_OK extends RecursiveTask<Long> {  
  
    final int n;  
  
    Fibonacci_OK(int n) {  
        this.n = n;  
    }  
  
    private long compute(int small) {  
        final long[] results = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89};  
        return results[small];  //这里[]中的值只能是int
    }  
  
    public Long compute() {  
        if (n <= 10) {  
            return compute(n);  
        }  
        Fibonacci_OK f1 = new Fibonacci_OK(n - 1);  
        f1.fork();  
        Fibonacci_OK f2 = new Fibonacci_OK(n - 2);
        long x=f2.compute() + f1.join();
        return x;  
    }  
  
    public static void main(String... args) throws InterruptedException, ExecutionException {  
        ForkJoinTask<Long> fjt = new Fibonacci_OK(30); //大于42时,int时48是负值溢出了 ,long值50时也是一样在win32下很慢 
        ForkJoinPool fjpool = new ForkJoinPool();  
        fjpool.execute(fjt);  
        Long resultInt = fjt.get();  
        System.out.println(resultInt);  
    }  
}  