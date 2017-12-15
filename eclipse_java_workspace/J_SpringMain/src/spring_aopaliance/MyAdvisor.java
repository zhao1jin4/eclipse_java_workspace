package spring_aopaliance;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

public class MyAdvisor implements PointcutAdvisor
{
	public Pointcut getPointcut()
	{
		return new MyPointcut();
	}
	public Advice getAdvice()
	{
		return new MyAdvice(); 
	}
	public boolean isPerInstance()
	{
		return false;
	}
}
