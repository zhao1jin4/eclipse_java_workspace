package spring_quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyQuartzJob implements Job{
 
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String data=context.getJobDetail().getJobDataMap().getString("data");
		System.out.println(" in MyQuartzJob ,data="+data);
	}

}
