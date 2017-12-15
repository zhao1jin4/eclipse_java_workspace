package spring_aop_proxy2;


import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class MyBeforeAdvice implements MethodBeforeAdvice
{

	public void before(Method method, Object[] args, Object target)
			throws Throwable
	{
		System.out.println("before!");
		if(args!=null && args.length > 0)
		{
			System.out.println(args[0]);
		}
	}

}
