package config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication  //等同用 @Configuration 和 , @EnableAutoConfiguration,@ComponentScan
public class MainApplication {
    public static void main(String[] args) {
    	//http://localhost:8081/J_SpringBoot/showConfig
    	SpringApplication.run(MainApplication.class, args);
    }
    
    @Value("${myprop.name:default_val}")//可以自定义值
    private String name;
    
    @Value("${myprop.desc}")
    private String desc;
    //---random
    @Value("${myprop.random.value}")
    private String random; //随机字串
    
    @Value("${myprop.random.int}")
    private int randomInt;
    
    @Value("${myprop.random.long}")
    private long randomLong;
    
    @Value("${myprop.random.int10}")//# 10以内的随机数
    private int randomIn10;
    
    @Value("${myprop.random.int10_20}")//10-20的随机数
    private int randomInt10_20;
     
    //java -jar xxx.jar --server.port=8888  也可以修改参数,会覆盖application.properties
/*
    application-dev.properties：开发环境
    application-test.properties：测试环境
    application-prod.properties：生产环境
	至于哪个具体的配置文件会被加载，需要在application.properties文件中通过spring.profiles.active属性来设置，其值对应{profile}值。
	
	java -jar xxx.jar --spring.profiles.active=test
*/
    
    @Autowired
    ConfigBean configBean;
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) 
    {
    	
    	  System.out.printf("myprop=%s\ndesc=%s\n",name,desc);
    	  System.out.printf("random=%s, randomInt=%s,randomLong=%s,randomIn10=%s,randomInt10_20=%s\n"
    			  ,random,randomInt,randomLong,randomIn10,randomInt10_20); 
    	  
    	  
    	  System.out.println( configBean.getName()+" >>>>"+ configBean.getAge());
    	  
        return args -> {//是CommandLineRunner 接口的一个run方法参数String[] 
            System.out.println("++++++++++++Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
