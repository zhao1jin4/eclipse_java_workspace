package spring_aop_aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyIntercepter 
{	
	
	
	//报错?????????
	//@Pointcut("execution(* spring_aop_aspectj.ABCService.*(..))")
	//@Pointcut("execution(* spring_aop_aspectj..*.*(..))")
	//private void myMethodPointcut(){}//是定义名字
	
	//@Before("myMethodPointcut()")//对应上面@Pointcut的名字
	//@Before("spring_aop_aspectj.ABCService.businessMethod()")
	
	
	
	@Before("execution(* spring_aop_aspectj.ABCService.*(..))")//OK ,execution后不能有空格,不用@Pointcut
	public void myBefore()
	{
		System.out.println("业务方法之前");
	}
	
	@Before("execution(* spring_aop_aspectj.ABCService.*(..)) && args(username)")//username要和参数中一致
	public void myBefore(String username)
	{
		System.out.println("业务方法之前,得到参数:"+username);
	}
	
	@AfterReturning("execution(* spring_aop_aspectj.ABCService.*(..))")
	public void myAfterReturning()
	{
		System.out.println("业务方法之后@AfterReturning");
	}
	
	@AfterReturning(pointcut="execution(* spring_aop_aspectj.ABCService.*(..))",returning="result")//result与方法参数对应
	public void myAfterReturning(String result)//对业务方法返回String的
	{
		System.out.println("业务方法之后@AfterReturning,得到返回值:"+result);
	}
	
	@After("execution(* spring_aop_aspectj.ABCService.*(..))")//在@AfterReturning之后,相当于在finally块中
	public void myAfter()
	{
		System.out.println("业务方法 最终 @After");
	}
	
	@AfterThrowing("execution(* spring_aop_aspectj.ABCService.*(..))")//相当于在catch块中
	public void myException()
	{
		System.out.println("业务异常的拦截");
	}
	
	@AfterThrowing(pointcut="execution(* spring_aop_aspectj.ABCService.*(..))",throwing="exception")//相当于在catch块中
	public void myException(Exception exception)
	{
		System.out.println("业务异常的拦截,异常原因是:"+exception.getMessage());
	}
	
	@Around("execution(* spring_aop_aspectj.ABCService.*(..))")
	public void myAround(ProceedingJoinPoint point) throws Throwable //必须有参数ProceedingJoinPoint
	{
		System.out.println("进入 @Around");//在@Before之后
		//可做权限判断
		point.proceed();//必须调用这个方法,才可调用到业务方法
		System.out.println("退出 @Around"); //在@AfterReturning之后,@After之前 
	}
	
}
