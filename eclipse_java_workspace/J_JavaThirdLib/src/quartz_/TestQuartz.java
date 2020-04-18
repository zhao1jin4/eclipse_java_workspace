package quartz_;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import  org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import static org.quartz.SimpleScheduleBuilder.*;
public class TestQuartz
{
	/*
	 *  
 <dependency>
  <groupId>org.quartz-scheduler</groupId>
  <artifactId>quartz</artifactId>
  <version>2.2.3</version>
</dependency>
<dependency>
  <groupId>org.quartz-scheduler</groupId>
  <artifactId>quartz-jobs</artifactId>
  <version>2.2.3</version>
</dependency>  

ʹ��c3p0����Դ
*/
 
	public static void quartz_2_2() throws Exception
	{
		SchedulerFactory schedFact = new StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler(); 
		
		sched.start();
		JobDetail job = JobBuilder.newJob(MyQuartzJob.class)
			//.usingJobData(dataKey, value)
//			.storeDurably().withDescription("desc")
			.withIdentity("myJob", "group1")
			.build();
  
		job.getJobDataMap().put("user_id", "zhangsan");
	 
		
		//------------ʮһ������Ҫcrond�г�
		HolidayCalendar holiday=new HolidayCalendar();
		Calendar nationalDay= Calendar.getInstance();
//		nationalDay.set(2018,Calendar.OCTOBER, 1);//ֻ, ������ ,�����õ�
		nationalDay.set(2018,Calendar.JULY, 3);
		
		holiday.addExcludedDate(nationalDay.getTime());
		sched.addCalendar("nationalDay", holiday, true, false);// boolean replace, boolean updateTriggers
		//------------
		Trigger trigger = TriggerBuilder.newTrigger()
		.withIdentity("myTrigger", "group1")
		//.startNow()
		.startAt(new java.util.Date(Calendar.getInstance().getTimeInMillis()+5000))
		
		.modifiedByCalendar("nationalDay") //���ü���
		
//		.withSchedule(SimpleScheduleBuilder.simpleSchedule()
//			.withIntervalInSeconds(3)
//			.withRepeatCount(10))//�����ǵ���Ͳ���������
		  //��ѡһ
		.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * 8-17 * * ?"))
		//.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMinutes(3))
		//.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(16, 0))
		//.forJob("myJob", "group1"); 
		
/*
//cron���ʽ,�ո�ָ���˳����
1.Seconds					0-59
2.Minutes					0-59
3.Hours						0-23
4.Day-of-Month				1-31
5.Month						between 0 and 11, or by using the strings JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV and DEC.
6.Day-of-Week				1 �� 7 (1 = Sunday) ,Ҳ����SUN, MON, TUE, WED, THU, FRI and SAT
7.Year (optional field)
-----
 '?' character is allowed for the day-of-month and day-of-week fields  ,"no specific value"
  'L' character is allowed for the day-of-month and day-of-week fields ,"last"
 		in the day-of-week field by itself, it simply means "7" or "SAT"
 		in the day-of-week field "6L" or "FRIL" both mean "the last friday of the month"
  '3/20' in the Minutes field, it would mean 'every 20th minute of the hour, starting at minute three' - or in other words it is the same as specifying '3,23,43' in the Minutes field
  "6#3" or "FRI#3" in the day-of-week field means "the third Friday of the month".
*/	  
			.build();
	
		  sched.scheduleJob(job, trigger);
		 
//		  sched.addJob(job,true);
//		  sched.scheduleJob(trigger);//triggerBuild.forJob
		  
//		  TriggerKey triggerKey=new TriggerKey("myTrigger","group1");
//		  sched.unscheduleJob(triggerKey);//ɾ���� 
		  
//		  JobKey jobKey=new JobKey("myJob","group1");
//		  sched.checkExists(jobKey);
		  /* 
		  List<Trigger> triggers=(List<Trigger>)sched.getTriggersOfJob(jobKey);//��ĳ����������ж�ʱ
		  for(Trigger trig:triggers)
		  {
			  TriggerKey triggerKey=trig.getKey();
			  TriggerState triggerState=sched.getTriggerState(triggerKey);
			  System.out.println(triggerState);//enum ,COMPLETED
		  }
		
		 */
//		  sched.triggerJob(jobKey);//������������
//		  sched.resumeTrigger(triggerKey);//����
//		  sched.pauseTrigger(triggerKey);//��ͣ
		 
		  /*	  
		  GroupMatcher<JobKey> matcher=GroupMatcher.groupEquals("group1");//�����д������
		  Set<JobKey> jobs=sched.getJobKeys(matcher);
		  for(JobKey key:jobs)
		  {
			  JobDetail detail=sched.getJobDetail(key);
			  detail.getClass();
			  detail.getDescription();
			  key.getName();
			  key.getGroup(); 
		  }
		*/
	}
	public static void quartz_file_jdbc_cluster()throws Exception
	{
		//�ֲ�ʽ ���� org.quartz.jobStore.isClustered = true
		
		
		//org/quartz/quartz.properties�ļ�,���Ա�src\�µ��ļ����� ,ʾ����quartz-2.2.3\examples\example10\quartz.properties
		//quartz.properties�в������ȥ�� quartz_data.xml
		//org/quartz/xml/job_scheduleing_data_2_0.xsd
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler(); //���classpath �µ�quartz.properties
        
        scheduler.start();   
        
        
        JobDetail job = JobBuilder.newJob(MyQuartzJob.class)
    			//.usingJobData(dataKey, value)
//    			.storeDurably().withDescription("desc")
    			.withIdentity("myJob", "group1")
    			.build();
      
    		job.getJobDataMap().put("user_id", "zhangsan");
    	Trigger trigger = TriggerBuilder.newTrigger()
    			.withIdentity("myTrigger", "group1")
    			//.startNow()
    			.startAt(new java.util.Date(Calendar.getInstance().getTimeInMillis()+5000))
    			 
    			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
    				.withIntervalInSeconds(3)
    				.withRepeatCount(10)) 
    			.build();
    			
    	scheduler.scheduleJob(job, trigger);
    			  
        //scheduler.shutdown(); 
        org.quartz.impl.jdbcjobstore.StdJDBCDelegate mysql; //mysql
        org.quartz.impl.jdbcjobstore.JobStoreCMT containerManageTransaction;
	}
	
	// java -DinstanceName=node1
	public static void main(String[] args)throws Exception
	{
		  String instance=System.getProperty("instanceName");//ȡ����-D��ֵ
		//quartz_2_2();
		quartz_file_jdbc_cluster(); //		
		//�ڲ��಻���� static�ı���
	}
}