package org.zhaojin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Daili implements InvocationHandler {

	Object temp;

	public Object bind(Object temp) {
		this.temp = temp;
		Object re = Proxy.newProxyInstance(temp.getClass().getClassLoader(),
				temp.getClass().getInterfaces(), this);
		System.out.println(re.getClass().getName() + ":"
		// +re.toString()
				);
		return re;

	}

	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {

		System.out.println("obj is :" + obj.getClass().getName());
		System.out.println(method.getName());

		System.out.println(args[0].getClass().getName());

		System.out.println("mouse is buy");
		Object o = method.invoke(temp, args);
		System.out.println(o.getClass().getName() + "::" + o.toString());
		return o;
	}

}
