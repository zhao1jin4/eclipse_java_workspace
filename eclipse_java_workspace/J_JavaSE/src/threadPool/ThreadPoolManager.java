package threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

/**
 * �̳߳صĹ����� ͨ����̬������ô����ʵ��
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
	 * ִ���߳�����,���ؽ��. ����̳߳�����ȫ��ռ��,����߳�����. �߳����еĴ���������ʵ�ַ���.
	 * 
	 * @param task
	 *            ʵ��java.util.concurrent.Callable�ӿڵ���
	 * @return �����߳����еĽ��
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public T runTask(Callable<T> task) throws InterruptedException,
			ExecutionException {
		semp.acquire();// ʹ���źű��,�����������������߳�����
		Future<T> f = service.submit(task);// �����߳�����,����ִ��
		semp.release();// �ź��ͷ�
		return f.get();// �����̴߳���Ľ��,������߳���δ�������,���߳�����ֱ�����ؽ��
	}

	/**
	 * @return �����߳̿�������
	 */
	public synchronized int getavailableNum() {
		return this.semp.availablePermits();
	}

	/**
	 * �ر��̳߳�
	 */
	public final void close() {
		service.shutdown();
	}
}
