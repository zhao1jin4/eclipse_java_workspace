package spring_aop_throws_tag;

import java.lang.reflect.Method;
import org.springframework.aop.ThrowsAdvice;

public class MyThrowsAdvice implements ThrowsAdvice{

//	public void afterThrowing(Method method, Object[] args, Object target, Throwable subclass)
	public void afterThrowing( Throwable subclass)
	
	{
		//System.out.println(method.getName()+"is INVOKED BY target is :"+target+"args is :"+args+"throws is :"+subclass);
		System.out.println("_________________");		
		
	}
	public MyThrowsAdvice()
	{
		System.out.println("cccccccccccccccccc");
	}
}
