package quiz;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Rabit {

	
	public static long rabit(long n)
	{	
		//java.lang.StackOverflowError
		if (n<=2)
			return 1;
		else 
		{
			int abc=1836311903;
					
			/*
			int i=1234567890l;
			    //1836311903 //45
			int  a=1234567890 + 1234567890;//�������
			long  b=1234567890l + 1234567890l;//��l
			*/
			
			long a=rabit(n-1);
			long b=rabit(n-2);
			long s=a+b;
			return s;
				
		
		}
	}
	
	
	
	//̶��ǿ  Fibonacci ��ѧ���� ���� ��������
	//���һ������ÿ������һ��С�ã�һ��һ�ƣ�����ÿ��С������������ĵ�����������ܿ�ʼ��һ��С��
	public static long rabit2(long n)// n=11;//����
	{
		long  f1=1,f2=1;
		for(int i=1;i<=n/2;i++)  // ��2�����������
		{
			System.out.printf("%12d  %12d\n",f1,f2);
			f1=f1+f2;
			f2=f2+f1;
		}
		return f1;
		
		
	}
	

	public static void main(String[] args) 
	{
		
		//java.lang.StackOverflowError
		//long count=rabit(41);//41=165580141 ,42=267914296, �紫42�Ļ��ǳ�����,�����͸�����˵��
		//long count=rabit2(5);//���õݹ��ٶȸ���,  
		//System.out.println("==="+count);
		
		
		
		//��һ����10���ڶ����˱ȵ�һ���˴�5�꣬��87���˶��
		int sumAge=10;
		for(int i=1;i<=87;i++)
		{
			sumAge+=5;
		}
		System.out.println(sumAge);
		
		int age=testRecursveCommon(10,1,87);
		//int age=testRecursveNew(10,1,87);
		System.out.println(age);
		
	}
	
	
	public static int testRecursveCommon(int age,int currentTimes,int totalTimes) 
	{
		if(currentTimes++  >totalTimes)
			return age;
		age=testRecursveCommon(age,currentTimes, totalTimes);
		age=age+5;
		return age;
	}
	
	
	public static void testRecursveNew(int age,int currentTimes,int totalTimes) 
	{
		
		ForkJoinPool pool = new ForkJoinPool(); //Fork/Join ģʽ ,Ĭ����runtime.availableProcessors();CPU���ٺ˵�
		pool.invoke(new   RecursiveAction() //��ʹ��RecursiveTask��compute�����ɴ�����ֵ
		{
			protected void compute()
			{
					
			//if(������)
			//		ֱ�ӵ���
			//else  ������
			//		�ݹ��ֶ��,һ���Ƕ��ַ�
			//		invokeAll(left,right); //invokeAll��ForkJoinTask�ķ���
			//		compute����û�з���ֵ,������Ա���ݹ�ֵ
			
//				.fork();//��������첽ִ��,ÿ�ε�������һ���߳�,ֱ�� runtime.availableProcessors()���߳�Ϊֹ,�������getSurplusQueuedTaskCount()����ֵ��1
//				.join()//�����ȴ������ɡ�
			}
		});
	}
	
	

}
