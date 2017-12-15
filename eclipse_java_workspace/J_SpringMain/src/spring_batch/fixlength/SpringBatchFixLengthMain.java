package spring_batch.fixlength;

import java.util.Formatter;
import java.util.Locale;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchFixLengthMain
{
 
    public static void testFixLength( )
    {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);

        formatter.format("%-9s %5d", "aabc", 232); //-左对齐 ,否则右对齐
        System.out.println(sb.toString());
        
        
        //----------------
        
        
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring_batch/fixlength/batch_fixlength.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("fixedLengthJob");

        try {
            // JOB实行
            JobExecution result = launcher.run(
                    job,
                    new JobParametersBuilder()
                            .addString("inputFilePath",
                                    "/tmp/student.csv")
                            .addString("outputFilePath",
                                    "/tmp/student_output.csv")
                            .toJobParameters());
            // 运行结果输出
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     
    public static void main(String[] args)
    {
        testFixLength();
    }
}
