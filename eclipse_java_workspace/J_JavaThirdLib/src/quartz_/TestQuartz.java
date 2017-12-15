package quartz_;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import  org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;

import static org.quartz.SimpleScheduleBuilder.*;
public class TestQuartz
{

	public static void quartz_2x() throws Exception
	{
		SchedulerFactory schedFact = new StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();
		sched.start();
		JobDetail job = JobBuilder.newJob(MyQuartzJob.class)
			.withIdentity("myJob", "group1")
			.build();
  
		job.getJobDataMap().put("user_id", "zhangsan");
	
		
		//------------十一假期在要crond中除
		HolidayCalendar holiday=new HolidayCalendar();
		Calendar nationalDay= Calendar.getInstance();
		//nationalDay.set(2012,Calendar.OCTOBER, 1);
		nationalDay.set(2013,Calendar.JANUARY, 6);//只, 年月日 ,是有用的
		
		holiday.addExcludedDate(nationalDay.getTime());
		sched.addCalendar("nationalDay", holiday, true, false);// boolean replace, boolean updateTriggers
		//------------
		Trigger trigger = TriggerBuilder.newTrigger()
		.withIdentity("myTrigger", "group1")
		//.startNow()
		.startAt(new java.util.Date(Calendar.getInstance().getTimeInMillis()+5000))
		
		.modifiedByCalendar("nationalDay") //设置假期
		
//		.withSchedule(SimpleScheduleBuilder.simpleSchedule()
//			.withIntervalInSeconds(3)
//			.withRepeatCount(10))
		      
		.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * 8-17 * * ?"))
/*
//cron表达式,空格分隔的顺序是
1.Seconds					0-59
2.Minutes					0-59
3.Hours						0-23
4.Day-of-Month				1-31
5.Month						between 0 and 11, or by using the strings JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV and DEC.
6.Day-of-Week				1 到 7 (1 = Sunday) ,也可用SUN, MON, TUE, WED, THU, FRI and SAT
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
		
	}
	public static void quartz_read_config_file()throws Exception
	{
		//org/quartz/quartz.properties文件,可以被src\下的文件覆盖 ,示例在quartz-2.1.6\examples\example10\quartz.properties
		//org/quartz/xml/job_scheduleing_data_2_0.xsd,
		
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler(); //会读classpath 下的quartz.properties
        scheduler.start();  //quartz.properties中配置去读 quartz_data.xml

        scheduler.shutdown(); 
        
        org.quartz.impl.jdbcjobstore.JobStoreCMT containerManageTransaction;
        org.quartz.impl.jdbcjobstore.oracle.OracleDelegate oracle;
	}
	public static void main(String[] args)throws Exception
	{
		
		quartz_2x();
		//quartz_read_config_file(); //未成功
		
		//内部类不能有 static的变量
	}
}