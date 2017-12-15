package spring_batch.jdbc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeoutException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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

import spring_batch.Student;

public class SpringBatchJDBCMain
{
    SqlPagingQueryProviderFactoryBean sqlFactryBean;
    JdbcPagingItemReader jdbcReader;
    JdbcCursorItemReader jdbcCursorItemReader;
    JpaPagingItemReader jpaReader;
    org.springframework.batch.item.file.FlatFileItemReader flatFileItemReader ;// 从文件中进行信息读入
    
    MongoItemWriter mongoWriter;
    JmsItemWriter jmsWriter;
     
    public static void jdbcPage( )
    { 
    
    ApplicationContext context = new ClassPathXmlApplicationContext( "spring_batch/jdbc/jdbc-batch.xml");
    JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
    Job job = (Job) context.getBean("jdbcPageJob");
    try {
        JobExecution result = launcher.run( job,  new JobParametersBuilder()
        //.addString("id", "10")
        .toJobParameters()  );
    }catch(Exception ex)
    {
        ex.printStackTrace();
    }
    
    
    }
    
    public static void jdbcCursor( )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext( "spring_batch/jdbc/jdbc-batch.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jdbcCursorJob");
        try {
            JobExecution result = launcher.run( job,
                    new JobParametersBuilder()
                            .addString("id", "10")
                            .toJobParameters()
                            );
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    public static void skip_validtor_incrementer( )
    {
        RunIdIncrementer x;
    }
    public static void main(String[] args)
    {
         jdbcPage();
         //jdbcCursor();
         
         skip_validtor_incrementer();
    }
}
