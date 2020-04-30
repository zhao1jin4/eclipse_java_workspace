package shiro_spring_anno_main;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
	public static void main(String[] args) {
		//https://shiro.apache.org/spring-framework.html
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CliAppConfig.class);
		
		System.out.println(SecurityUtils.getSubject());
	
	}
}
