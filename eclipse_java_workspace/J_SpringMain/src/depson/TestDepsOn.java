package depson;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestDepsOn {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(Config.class);
		//ClassA a= ctx.getBean(ClassA.class);
		ClassB b= ctx.getBean(ClassB.class);
		b=null;
		ClassB b1= ctx.getBean(ClassB.class);//B是多例的，每次调用GetBean都会调用初始化方法
		
	}

}
