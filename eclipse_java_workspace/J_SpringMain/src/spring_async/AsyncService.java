package spring_async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
	@Async //线程异步
	public void asyncFunction() {
		while(true) {
			System.out.println("异步方法处理中");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
