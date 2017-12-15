package threadPool.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

//RecursiveAction 文档中的示例,计算数组中的每个数的平方,最后相再加
class Applyer extends RecursiveAction
{
   final double[] array;
   final int lo, hi;
   double result;
   Applyer next; // keeps track of right-hand-side tasks
   Applyer(double[] array, int lo, int hi, Applyer next) {
     this.array = array; this.lo = lo; this.hi = hi;
     this.next = next;
   }

   double atLeaf(int l, int h) {
     double sum = 0;
     for (int i = l; i < h; ++i) // perform leftmost base step
       sum += array[i] * array[i];
     return sum;
   }

   protected void compute() {
     int l = lo;
     int h = hi;
     Applyer right = null;
     while (h - l > 1 && getSurplusQueuedTaskCount() <= 3)//JDK中的方法 getSurplusQueuedTaskCount(),最开始-1,fork()后为0
     {
        int mid = (l + h) >>> 1;
        right = new Applyer(array, mid, h, right);
        right.fork();//每次调用增加一个线程,直到 runtime.availableProcessors()个线程为止,如果超过getSurplusQueuedTaskCount()返回值加1
        h = mid;
     }
     double sum = atLeaf(l, h);
     while (right != null) 
     {
        if (right.tryUnfork()) // directly calculate if not stolen  ,//JDK中的方法 tryUnfork()
          sum += right.atLeaf(right.lo, right.hi);
       else 
       {
          right.join();	//JDK中的方法 join()
          sum += right.result;
        }
        right = right.next;
      }
     result = sum;
   }
   public static void main(String[] args) 
   {
	   		Runtime runtime=Runtime.getRuntime();
	   		int cpus=runtime.availableProcessors();
		   ForkJoinPool pool=new ForkJoinPool();//默认是runtime.availableProcessors();CPU多少核的
		   double[]  array =new double[]{22.2,11.1,10.0,8.8,22.2,11.1,10.0,8.8,22.2,11.1,10.0,8.8,22.2,11.1,10.0,8.8};
		   int n = array.length;
		   Applyer a = new Applyer(array, 0, n, null);
		   pool.invoke(a);
		   System.out.println(a.result);

   }
}