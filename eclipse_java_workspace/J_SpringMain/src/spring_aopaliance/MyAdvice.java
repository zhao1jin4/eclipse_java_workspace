package spring_aopaliance;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAdvice implements MethodInterceptor
{

	public Object invoke(MethodInvocation method) throws Throwable
	{
		Object o=new Object();
		System.out.println("before");
		
		o=method.proceed();
		System.out.println("after");
		return o;
	}
	
}
