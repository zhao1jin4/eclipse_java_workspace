package depson;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestDepsOn {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(Config.class);
		//ClassA a= ctx.getBean(ClassA.class);
		ClassB b= ctx.getBean(ClassB.class);
		b=null;
		ClassB b1= ctx.getBean(ClassB.class);//B�Ƕ����ģ�ÿ�ε���GetBean������ó�ʼ������
		
	}

}
