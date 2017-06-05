package spring_aop_throws_tag;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAroundInterceptor implements MethodInterceptor
{

	public Object invoke(MethodInvocation invo) throws Throwable
	{
		Object o=new Object();
		System.out.println("this is before invoke by MehtodIntercepter");
		if (ILogin.class.isAssignableFrom(invo.getMethod().getDeclaringClass()))
		{
			 o=invo.proceed();	
		}
	//	Object o=invo.proceed();
		System.out.println("this is after invoke by MehtodIntercepter");
		return o;
	}
}
