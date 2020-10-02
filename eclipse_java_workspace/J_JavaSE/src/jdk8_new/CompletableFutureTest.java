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
			myRe = re.get() * re.get();// 依赖于等待另一个线程计算完成 ,等future.complete(60)完成
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
		Thread.sleep(1000);//模拟长时间
		future.complete(60); // 告知完成结果，传值 
	}
	public static void simple( ) throws   Exception
	{
		final CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> calc(5));//异步自动开线程 forkjoinPool
		System.out.println(future1.get());
		
		
		final CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> calc(3));
		CompletableFuture.allOf(future1,future2).join();
		
		 CompletableFuture<Void> fu = CompletableFuture .supplyAsync(() -> calc(5)) 
				.thenApply((i) -> Integer.toString(i))
				.thenApply((str) -> "\"" + str + "\"")
				.thenAccept(System.out::println);
		 fu.get();
		 
		 
		 CompletableFuture<Void> fu1 = CompletableFuture.supplyAsync(() -> calc(50))
				//把上一个计算结果 传入下面的i
				 .thenCompose(
						 		(i) -> CompletableFuture.supplyAsync(() -> calc(i))
						 )
				 .thenApply((str) -> "\"" + str + "\"")
				 .thenAccept(System.out::println);
		 
		 fu1.get();
	}

	public static Integer calc(Integer para) {
		try {
			System.out.println("模拟"+para+"秒开始");
			Thread.sleep(para*1000); // 模拟一个长时间的执行
			System.out.println("模拟"+para+"秒完成");
		} catch (InterruptedException e) {
		}
		return para * para;
	}
	 
	
}