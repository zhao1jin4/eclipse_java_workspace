package spring_quartz;
import java.util.Calendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringMain
{
	public static void timer() throws Exception
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring_quartz/timer_benas.xml");
		Thread.sleep(90000*1000);
		
	}
	public static void quartz() throws Exception
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring_quartz/quartz_benas.xml");
		Thread.sleep(10*1000);
		
		//�޸Ŀɳ־û������� 
		org.quartz.impl.jdbcjobstore.JobStoreCMT containerManageTransaction;
		org.quartz.impl.jdbcjobstore.oracle.OracleDelegate oracle;
		//
		Scheduler scheduler=(Scheduler)context.getBean("schedulerFactory");
		JobDetail myJobStore=scheduler.getJobDetail(new JobKey("myStoreJob"));
		if(myJobStore==null)//jobÿ�ζ����ܴ�DB��ȡ����??????
		{
			myJobStore = JobBuilder.newJob(MyQuartzJob.class)
					.build();
			myJobStore.getJobBuilder().withIdentity(new JobKey("myStoreJob"));
			myJobStore.getJobDataMap().put("data", "Java�����е�����");
		}
		Trigger trigger= scheduler.getTrigger(new TriggerKey("myStoreTrigger"));
		if(trigger==null)//trigger���Դ�DB��ȡ��
		{
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("myStoreTrigger")
			//.startNow()
			.startAt(new java.util.Date(Calendar.getInstance().getTimeInMillis()+5000))
			//.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * 8-17 * * ?"))
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(5)
				.withRepeatCount(4))
			.build();
		} 
		scheduler.scheduleJob(myJobStore, trigger);//���trigger�Ǵ�DB��ȡ,��job��new��,���ǲ��е�??????
		
		
		//���ʹ��ָ��·����log4j.xml �޸������־����??????
		Thread.sleep(5000*1000);
		scheduler.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception
	{
		//timer();
		quartz();
	}

}