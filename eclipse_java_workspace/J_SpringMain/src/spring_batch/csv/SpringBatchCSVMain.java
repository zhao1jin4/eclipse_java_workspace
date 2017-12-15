package spring_batch.csv;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

public class SpringBatchCSVMain
{
  
    public static void testCSV( )
    {
        ClassPathXmlApplicationContext context =   new ClassPathXmlApplicationContext("spring_batch/csv/batch.xml");
       SimpleJobLauncher launcher = new SimpleJobLauncher();
       launcher.setJobRepository((JobRepository) context.getBean("jobRepository"));
       launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
       try {
            launcher.run((Job) context.getBean("csvJob"), new JobParameters());
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    
     
    public static void main(String[] args)
    {
        testCSV();
    }
}
