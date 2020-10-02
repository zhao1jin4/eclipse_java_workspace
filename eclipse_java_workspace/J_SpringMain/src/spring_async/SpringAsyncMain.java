package spring_async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan
@EnableAsync //线程池做异步
//@EnableXxx里面一般有一个@ImportXxx的注释，一个配置类上有@Configuration 
public class SpringAsyncMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(SpringAsyncMain.class);
		AsyncService service=ctx.getBean(AsyncService.class);
		service.asyncFunction();
		System.out.println("main最后一个打印");
	}
}
