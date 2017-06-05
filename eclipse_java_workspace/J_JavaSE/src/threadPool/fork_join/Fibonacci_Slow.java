package threadPool.fork_join;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
  
class Fibonacci_Slow extends RecursiveTask<Integer> {  
  
    final int n;  
  
    Fibonacci_Slow(int n) {  
        this.n = n;  
    }  
  
    private int compute(int small) {  
        final int[] results = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89};  
        return results[small];  
    }  
  
    public Integer compute() {  
        if (n <= 10) {  
            return compute(n);  
        }  
         
        Fibonacci_Slow f1 = new Fibonacci_Slow(n - 1);  
        Fibonacci_Slow f2 = new Fibonacci_Slow(n - 2);  
        f1.fork();  
        f2.fork();  
        return f1.join() + f2.join();  
    }  
  
    public static void main(String... args) throws InterruptedException, ExecutionException {  
   ForkJoinTask<Integer> fjt = new Fibonacci_Slow(30);  
        ForkJoinPool fjpool = new ForkJoinPool();  
        fjpool.execute(fjt);  
        Integer resultInt = fjt.get();  
        System.out.println(resultInt); 
    }  
}  