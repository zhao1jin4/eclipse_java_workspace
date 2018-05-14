package jdk8_new;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest implements Runnable {
	CompletableFuture<Integer> re = null;

	public CompletableFutureTest(CompletableFuture<Integer> re) {
		this.re = re;
	}

	@Override
	public void run() {
		int myRe = 0;
		try {
			myRe = re.get() * re.get();// �����ڵȴ���һ���̼߳������ ,��future.complete(60)���
		} catch (Exception e) {
		}
		System.out.println(myRe);
	}

	public static void main(String[] args) throws   Exception
	{
		
		//useThread( );
		simple( );
	}
	
	public static void useThread( ) throws   Exception
	{

		final CompletableFuture<Integer> future = new CompletableFuture<Integer>();
		new Thread(new CompletableFutureTest(future)).start();
		Thread.sleep(1000);//ģ�ⳤʱ��
		future.complete(60); // ��֪��ɽ������ֵ 

		 
	}
	public static void simple( ) throws   Exception
	{
		final CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> calc(50));//�첽�Զ����߳� forkjoinPool
		System.out.println(future1.get());
		
		 CompletableFuture<Void> fu = CompletableFuture .supplyAsync(() -> calc(50)) 
				.thenApply((i) -> Integer.toString(i))
				.thenApply((str) -> "\"" + str + "\"")
				.thenAccept(System.out::println);
		 fu.get();
		 
		 
		 CompletableFuture<Void> fu1 = CompletableFuture.supplyAsync(() -> calc(50))
				//����һ�������� ���������i
				 .thenCompose(
						 		(i) -> CompletableFuture.supplyAsync(() -> calc(i))
						 )
				 .thenApply((str) -> "\"" + str + "\"")
				 .thenAccept(System.out::println);
		 
		 fu1.get();
	}

	public static Integer calc(Integer para) {
		try {
			Thread.sleep(1000); // ģ��һ����ʱ���ִ��
		} catch (InterruptedException e) {
		}
		return para * para;
	}
	 
	
}