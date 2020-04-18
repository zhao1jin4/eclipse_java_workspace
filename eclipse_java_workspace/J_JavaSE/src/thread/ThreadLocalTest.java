package thread;

public class ThreadLocalTest {
	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				
				 
				//如设计为 静态方法Thread.set(xxx)，Thread有个静态map，key是Thread.currentThread()，线程结束清数据难？？？？
				
				
				//ThreadLocal 不能声明为static(因是多个线程共享)
				//内部是一个初始16长度的Entry数组,如不够用会扩容的，
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
				ThreadLocal 里存在线程里的（Thread.currentThread()得到）中的一个属性threadLocals里类型为ThreadLocalMap类型
				(内部是一个初始16长度的Entry数组,如不够用会扩容的,如一个线程使用多ThreadLocal对象个使用this区分)
				值Entry类型是WeakReference， Entry构造器是把key(即threadLocal this)引用调用父类WeakReference(threadLocal是弱引用，如threadLocal=null就会被回收),value不是,没有被回收内存溢出
 					线程一直在,threadLocal一直在，如threadLocal只一个一存在的强引用，下次再set冲前面的，即使用不调用remove()没问题 
				*/	
				//ThreadLocal<String> local1=new ThreadLocal<>();
				for(int i=0;i<100;i++)
				{
					//每次new一个，方法结束也不是释放 因为被线程中的数组引用
					//如ThreadLocal在里面,没有引用，内部弱引用，key会被回收，value没被回收
					ThreadLocal<String> local1=new ThreadLocal<>();//tab数组超16，变多，会内存溢出
					local1.set("xxx"+i);//是放在线程中的
					System.out.println(local1.get());
					local1.remove();//如每次调用new ThreadLoca()，用完一定最要用remove（）
					if(i==98)
					{
						System.out.println(local1.get());
					}
				}
			 
			}
			
		}.start();
	}
}
