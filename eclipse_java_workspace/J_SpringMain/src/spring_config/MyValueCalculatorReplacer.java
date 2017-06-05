package spring_config;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;

public class MyValueCalculatorReplacer implements MethodReplacer{
	public Object reimplement(Object obj, Method method, Object[] args)	throws Throwable {
		String str=(String)args[0];
		return str+"789";//ÕâÀï×öÌæ»»
	}
}
