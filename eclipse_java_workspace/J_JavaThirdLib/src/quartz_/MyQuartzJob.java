package quartz_;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

@DisallowConcurrentExecution
public class MyQuartzJob implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException { 
	    JobKey jobKey = context.getJobDetail().getKey();
	    String user_id=context.getJobDetail().getJobDataMap().getString("user_id");
		System.out.println("in MyQuartzJob ,key= "+jobKey+",user_id="+user_id);
	}
}
