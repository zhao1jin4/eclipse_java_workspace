package spring_aopaliance;

import org.springframework.aop.ClassFilter;

import spring_aop_throws_tag.ILogin;

public class MyClassFilter implements ClassFilter
{

	public boolean matches( Class c)
	{
		if (ILogin.class.isAssignableFrom(c))
		{
			return true;
		} else
			return false;
	}

}
