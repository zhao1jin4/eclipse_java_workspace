package threadPool.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

//RecursiveAction �ĵ��е�ʾ��,���������е�ÿ������ƽ��,������ټ�
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
     while (h - l > 1 && getSurplusQueuedTaskCount() <= 3)//JDK�еķ��� getSurplusQueuedTaskCount(),�ʼ-1,fork()��Ϊ0
     {
        int mid = (l + h) >>> 1;
        right = new Applyer(array, mid, h, right);
        right.fork();//ÿ�ε�������һ���߳�,ֱ�� runtime.availableProcessors()���߳�Ϊֹ,�������getSurplusQueuedTaskCount()����ֵ��1
        h = mid;
     }
     double sum = atLeaf(l, h);
     while (right != null) 
     {
        if (right.tryUnfork()) // directly calculate if not stolen  ,//JDK�еķ��� tryUnfork()
          sum += right.atLeaf(right.lo, right.hi);
       else 
       {
          right.join();	//JDK�еķ��� join()
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
		   ForkJoinPool pool=new ForkJoinPool();//Ĭ����runtime.availableProcessors();CPU���ٺ˵�
		   double[]  array =new double[]{22.2,11.1,10.0,8.8,22.2,11.1,10.0,8.8,22.2,11.1,10.0,8.8,22.2,11.1,10.0,8.8};
		   int n = array.length;
		   Applyer a = new Applyer(array, 0, n, null);
		   pool.invoke(a);
		   System.out.println(a.result);

   }
}