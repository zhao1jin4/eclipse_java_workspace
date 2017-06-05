package spring_aop_introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class LockMixinIntercepter extends DelegatingIntroductionInterceptor implements ILockable,IAnother 
										//implements IntroductionInterceptor, ILockable
{

	boolean lock = false;

	public Object invoke(MethodInvocation invocation) throws Throwable
	{

		/*if (implementsInterface(invocation.getMethod().getDeclaringClass()))
		{
			String methodName = invocation.getMethod().getName();
			System.out.println(methodName);// ------
			if (lock && "write".equals(methodName))
			{
				throw new RuntimeException("方法已被锁定");
			} else
				return invocation.getMethod().invoke(this,
						invocation.getArguments());
		}*/
///////////////
		
		
		String methodName = invocation.getMethod().getName();
		System.out.println(methodName);// ------
		if (lock && "write".equals(methodName))
		{
			throw new RuntimeException("方法已被锁定");
		}
		/*else if (implementsInterface(invocation.getMethod()
				.getDeclaringClass()))
		{
			return invocation.getMethod().invoke(this,
					invocation.getArguments());
		}

		return invocation.proceed();
		//DelegatingIntroductionInterceptor
		*/
		return super.invoke(invocation);
	}

/*	public boolean implementsInterface(Class clazz)
	{
		return (ILockable.class.isAssignableFrom(clazz));
	}
//DelegatingIntroductionInterceptor
 */
	public boolean isLock()
	{
		return lock;
	}

	public void lock()
	{
		lock = true;
	}

	public void unLock()
	{
		lock = false;
	}


	public void printHello() {
		System.out.println("hello in introduction---------");
	}

}
