package spring_aopaliance;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

public class MyPointcut implements Pointcut
{
	public ClassFilter getClassFilter()
	{
		return new MyClassFilter();
	}

	public MethodMatcher getMethodMatcher()
	{

		return new MyMehtodMatcher();
	}

}
