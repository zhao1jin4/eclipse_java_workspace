package thread;

public class ThreadLocalTest {
	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				
				 
				//�����Ϊ ��̬����Thread.set(xxx)��Thread�и���̬map��key��Thread.currentThread()���߳̽����������ѣ�������
				
				
				//ThreadLocal ��������Ϊstatic(���Ƕ���̹߳���)
				//�ڲ���һ����ʼ16���ȵ�Entry����,�粻���û����ݵģ�
				/*
				ThreadLocal<String> local1=new ThreadLocal<>();
				local1.set("one");
				ThreadLocal<String> local2=new ThreadLocal<>();
				local2.set("two");
				ThreadLocal<String> local3=new ThreadLocal<>();
				local3.set("three");
				ThreadLocal<String> local4=new ThreadLocal<>();
				local4.set("four");
				ThreadLocal<String> local5=new ThreadLocal<>();
				local5.set("five");
				ThreadLocal<String> local6=new ThreadLocal<>();
				local6.set("six");
				ThreadLocal<String> local7=new ThreadLocal<>();
				local7.set("seven");
				ThreadLocal<String> local8=new ThreadLocal<>();
				local8.set("eight");
				ThreadLocal<String> local9=new ThreadLocal<>();
				local9.set("nign");
				ThreadLocal<String> local10=new ThreadLocal<>();
				local10.set("ten");
				
				ThreadLocal<String> local11=new ThreadLocal<>();
				local11.set("1one");
				ThreadLocal<String> local12=new ThreadLocal<>();
				local12.set("1two");
				ThreadLocal<String> local13=new ThreadLocal<>();
				local13.set("1three");
				ThreadLocal<String> local14=new ThreadLocal<>();
				local14.set("1four");
				ThreadLocal<String> local15=new ThreadLocal<>();
				local15.set("1five");
				ThreadLocal<String> local16=new ThreadLocal<>();
				local16.set("1six");
				ThreadLocal<String> local17=new ThreadLocal<>();
				local17.set("1seven");
				
				System.out.println(local1.get());
				System.out.println(local2.get());
				System.out.println(local3.get());
				System.out.println(local4.get());
				System.out.println(local5.get());
				System.out.println(local6.get());
				System.out.println(local7.get());
				System.out.println(local8.get());
				System.out.println(local9.get());
				System.out.println(local10.get());
				System.out.println(local11.get());
				System.out.println(local12.get());
				System.out.println(local13.get());
				System.out.println(local14.get());
				System.out.println(local15.get());
				System.out.println(local16.get());
				System.out.println(local17.get());
				*/
				/*
				ThreadLocal ������߳���ģ�Thread.currentThread()�õ����е�һ������threadLocals������ΪThreadLocalMap����
				(�ڲ���һ����ʼ16���ȵ�Entry����,�粻���û����ݵ�,��һ���߳�ʹ�ö�ThreadLocal�����ʹ��this����)
				ֵEntry������WeakReference�� Entry�������ǰ�key(��threadLocal this)���õ��ø���WeakReference(threadLocal�������ã���threadLocal=null�ͻᱻ����),value����,û�б������ڴ����
 					�߳�һֱ��,threadLocalһֱ�ڣ���threadLocalֻһ��һ���ڵ�ǿ���ã��´���set��ǰ��ģ���ʹ�ò�����remove()û���� 
				*/	
				//ThreadLocal<String> local1=new ThreadLocal<>();
				for(int i=0;i<100;i++)
				{
					//ÿ��newһ������������Ҳ�����ͷ� ��Ϊ���߳��е���������
					//��ThreadLocal������,û�����ã��ڲ������ã�key�ᱻ���գ�valueû������
					ThreadLocal<String> local1=new ThreadLocal<>();//tab���鳬16����࣬���ڴ����
					local1.set("xxx"+i);//�Ƿ����߳��е�
					System.out.println(local1.get());
					local1.remove();//��ÿ�ε���new ThreadLoca()������һ����Ҫ��remove����
					if(i==98)
					{
						System.out.println(local1.get());
					}
				}
			 
			}
			
		}.start();
	}
}
