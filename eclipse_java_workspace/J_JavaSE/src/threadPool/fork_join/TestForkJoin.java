package threadPool.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;


public class TestForkJoin {

	public static void main(String... args)throws Exception //main的新写法
	{
		 ForkJoinPool pool = new ForkJoinPool(10); // Fork/Join 模式 ,多核CPU
		 pool.invoke(new MySortTask()); //会调用RecursiveAction 的 compute 方法
		 
		 
		//第二种带返回值的
		RecursiveTask<Integer> task=new MyRecursiveTask (); 
		pool.execute(task);//变execute
		Integer result = task.get();
		
		
		AtomicInteger integer;
	}	
}
class MyRecursiveTask extends RecursiveTask<Integer> {  //变RecursiveTask
 public Integer compute() {
	//可调用fork/join/compute
	return null;  
 }
}


//fork 餐叉,分叉/join
	//ForkJoinPool 
	 class MySortTask extends RecursiveAction  //递归的 ,extends ForkJoinTask,如使用RecursiveTask的compute方法可带返回值
	 {
		 protected void compute() //实现方法
		   {
			 System.out.println("RecursiveAction compute ");
//			 if(数量少)
//				  直接调用
//			 else  数量多
//				递归拆分 多个
//				invokeAll(left,right); //invokeAll是ForkJoinTask的方法
//			 	compute方法没有返回值,这里可以保存递归值
			 
			 
			 
		   }
	 }