package spring_quartz.other;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class MySomeBus 
{
	@Scheduled(fixedDelay=5000)
	public void doBusAtRate()
	{
		System.out.println("===MySomeBus__doBusAtRate");
	}
	@Scheduled(cron="*/5 * * * * MON-FRI")
	public void doBusCrond()
	{
		System.out.println("===MySomeBus__doBusCrond");
	}
	
	@Async
	public void asyncReturnSomething() 
	{
		System.out.println("===MySomeBus__asyncReturnSomething__busy...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("===MySomeBus__asyncReturnSomething done.");
	}
	
//	@Async
//	Future<String> returnSomething(int i) {
//	}
}
