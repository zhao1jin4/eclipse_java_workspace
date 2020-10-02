package spring_async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan
@EnableAsync //�̳߳����첽
//@EnableXxx����һ����һ��@ImportXxx��ע�ͣ�һ������������@Configuration 
public class SpringAsyncMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(SpringAsyncMain.class);
		AsyncService service=ctx.getBean(AsyncService.class);
		service.asyncFunction();
		System.out.println("main���һ����ӡ");
	}
}
