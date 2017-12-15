package spring_batch.flat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeoutException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

public class SpringBatchPlainMain
{
 
   public static void testNetException() 
   {
        try
        {
            URLConnection conn=new URL("http://google.com").openConnection();
            int timeout= conn.getConnectTimeout();
            conn.getReadTimeout();
            conn.setConnectTimeout(3000); //java.net.SocketTimeoutException
           
         
            conn.connect();
        }catch(java.net.SocketTimeoutException timeOutExc)
        {
            timeOutExc.printStackTrace();
        } catch ( Exception e)
        {
            e.printStackTrace();
        }
        java.sql.SQLException d;
        java.sql.SQLTimeoutException e;
        
    }
    public static void fileReaderProcessWriter( )
    {
        ClassPathXmlApplicationContext context =   new ClassPathXmlApplicationContext("spring_batch/flat/file-batch.xml");
       SimpleJobLauncher launcher = new SimpleJobLauncher();
       launcher.setJobRepository((JobRepository) context.getBean("jobRepository"));
       launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
       try {
           //JobParameters 相同的任务只能成功运行一次,允许重复执行未成功的 Job
           //配置retry-limit="2"自动重试 (用于网络临时断开,或者数据库临时失败)
           //如果入参有问题不适用
            launcher.run((Job) context.getBean("messageJob"), new JobParameters());
            //launcher.run((Job) context.getBean("messageJob"), new JobParameters());
             
            
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    
    public static void jobStep( )
    {
        org.springframework.retry.policy.RetryContextCache c ;
        //依赖于 spring-retry-1.1.0.RELEASE.jar
        
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_batch/flat/job_step.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("helloWorldJob");
        try {
            JobExecution result = launcher.run(job, new JobParameters());
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        //testNetException();
        fileReaderProcessWriter();
       // jobStep();
        
    }
}
