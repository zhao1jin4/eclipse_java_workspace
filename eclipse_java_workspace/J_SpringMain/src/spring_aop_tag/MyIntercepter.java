package spring_aop_tag;

import org.aspectj.lang.ProceedingJoinPoint;

public class MyIntercepter 
{	
	
	public void myBefore()
	{
		System.out.println("业务方法之前");
	}
	
	public void myBefore(String username)
	{
		System.out.println("业务方法之前,得到参数:"+username);
	}
	
	public void myAfterReturning()
	{
		System.out.println("业务方法之后@AfterReturning");
	}
	
	public void myAfterReturning(String result)
	{
		System.out.println("业务方法之后@AfterReturning,得到返回值:"+result);
	}
	
	public void myAfter()
	{
		System.out.println("业务方法 最终 @After");
	}
	
	public void myException()
	{
		System.out.println("业务异常的拦截");
	}
	
	public void myException(Exception exception)
	{
		System.out.println("业务异常的拦截,异常原因是:"+exception.getMessage());
	}
	public void myAround(ProceedingJoinPoint point) throws Throwable //必须有参数ProceedingJoinPoint
	{
		System.out.println("进入 @Around");//在@Before之后
		//可做权限判断
		point.proceed();//必须调用这个方法,才可调用到业务方法
		
		StringBuffer message = new StringBuffer();
		message.append(point.getTarget().getClass().getSimpleName()).append(".").append(point.getSignature().getName()).append(";");
		System.out.println(message.toString()); 
		
		System.out.println("退出 @Around"); //在@AfterReturning之后,@After之前 
	}
	
}
