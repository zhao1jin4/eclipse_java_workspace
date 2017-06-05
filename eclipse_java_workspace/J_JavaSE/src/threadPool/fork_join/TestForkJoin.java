package threadPool.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;


public class TestForkJoin {

	public static void main(String... args)throws Exception //main����д��
	{
		 ForkJoinPool pool = new ForkJoinPool(10); // Fork/Join ģʽ ,���CPU
		 pool.invoke(new MySortTask()); //�����RecursiveAction �� compute ����
		 
		 
		//�ڶ��ִ�����ֵ��
		RecursiveTask<Integer> task=new MyRecursiveTask (); 
		pool.execute(task);//��execute
		Integer result = task.get();
		
		
		AtomicInteger integer;
	}	
}
class MyRecursiveTask extends RecursiveTask<Integer> {  //��RecursiveTask
 public Integer compute() {
	//�ɵ���fork/join/compute
	return null;  
 }
}


//fork �Ͳ�,�ֲ�/join
	//ForkJoinPool 
	 class MySortTask extends RecursiveAction  //�ݹ�� ,extends ForkJoinTask,��ʹ��RecursiveTask��compute�����ɴ�����ֵ
	 {
		 protected void compute() //ʵ�ַ���
		   {
			 System.out.println("RecursiveAction compute ");
//			 if(������)
//				  ֱ�ӵ���
//			 else  ������
//				�ݹ��� ���
//				invokeAll(left,right); //invokeAll��ForkJoinTask�ķ���
//			 	compute����û�з���ֵ,������Ա���ݹ�ֵ
			 
			 
			 
		   }
	 }