package spring_aopaliance;

import java.lang.reflect.Method;

import org.springframework.aop.MethodMatcher;

public class MyMehtodMatcher implements MethodMatcher
{

	public boolean isRuntime()
	{
		return false;
	}

	public boolean matches(Method method, Class c)
	{
		if (c.isAssignableFrom(c)&& method.getName().equals("login"))
		{
			return true;
		}
		return false;
	}

	public boolean matches(Method arg0, Class arg1, Object[] arg2)
	{
		return false;
	}

}
