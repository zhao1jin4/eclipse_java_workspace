package spring_batch.xml;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

public class SpringBatchXMLMain
{
  
    //依赖 于xmlpull.jar org/xmlpull/v1/XmlPullParserException

//  报  java.lang.NullPointerException
//    at org.springframework.batch.item.xml.StaxEventItemWriter.close(StaxEventItemWriter.java:659)
//    的原因是路径文件找不到
    
    public static void testXML( )
    {
        org.xmlpull.v1.XmlPullParserException x;
        ClassPathXmlApplicationContext context =   new ClassPathXmlApplicationContext("spring_batch/xml/xml_batch.xml");
       SimpleJobLauncher launcher =(SimpleJobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("xmlFileReadAndWriterJob");

        try {
            // JOB实行
            JobExecution result = launcher.run(job, new JobParametersBuilder()
                    .addString("inputFilePath", "/tmp/input.xml")
                    .addString("outputFilePath", "/tmp/output.xml")
                    .toJobParameters());
            //"classpath:/spring_batch/xml/input.xml"
            
            // 运行结果输出
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     
    public static void main(String[] args)
    {
        testXML();
    }
}
