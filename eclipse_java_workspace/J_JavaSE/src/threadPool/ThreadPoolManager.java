package threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

/**
 * 线程池的管理类 通过静态方法获得此类的实例
 */
public class ThreadPoolManager<T> {
	private ExecutorService service = null;
	private final Semaphore semp;

	private int maxNum = 3;

	private static ThreadPoolManager me = null;

	public static ThreadPoolManager getInstance() {
		if (me == null) {
			me = new ThreadPoolManager();
		}
		return me;
	}

	public static ThreadPoolManager getInstance(int maxNum) {
		if (me == null) {
			me = new ThreadPoolManager(maxNum);
		}
		return me;
	}

	private ThreadPoolManager() {
		service = Executors.newCachedThreadPool();
		semp = new Semaphore(maxNum);
	}

	private ThreadPoolManager(int maxNum) {
		this.maxNum = maxNum;
		service = Executors.newCachedThreadPool();
		semp = new Semaphore(maxNum);
	}

	/**
	 * 执行线程任务,返回结果. 如果线程池数量全部占用,则此线程阻塞. 线程运行的传入参数类的实现方法.
	 * 
	 * @param task
	 *            实现java.util.concurrent.Callable接口的类
	 * @return 返回线程运行的结果
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public T runTask(Callable<T> task) throws InterruptedException,
			ExecutionException {
		semp.acquire();// 使用信号标记,如果超过个数会造成线程阻塞
		Future<T> f = service.submit(task);// 加载线程任务,并且执行
		semp.release();// 信号释放
		return f.get();// 返回线程处理的结果,如果此线程尚未处理完成,则线程阻塞直到返回结果
	}

	/**
	 * @return 返回线程可用数量
	 */
	public synchronized int getavailableNum() {
		return this.semp.availablePermits();
	}

	/**
	 * 关闭线程池
	 */
	public final void close() {
		service.shutdown();
	}
}
