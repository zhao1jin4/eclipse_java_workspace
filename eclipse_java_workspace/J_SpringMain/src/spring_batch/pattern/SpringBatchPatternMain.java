package spring_batch.pattern;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchPatternMain
{
 
    public static void testPattern( )
    {
       
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring_batch/pattern/batch_pattern.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("multiTypeSingleFileJob");

        try {
            // JOB实行
            JobExecution result = launcher.run(
                    job,
                    new JobParametersBuilder()
                            .addString("inputFilePath",
                                    "/tmp/student_goods.csv")
                            .addString("outputFilePathStudent",
                                    "/tmp/student.txt")
                            .addString("outputFilePathGoods",
                                    "/tmp/goods.csv")
                            .toJobParameters());
            // 运行结果输出
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     
    public static void main(String[] args)
    {
        testPattern();
    }
}
