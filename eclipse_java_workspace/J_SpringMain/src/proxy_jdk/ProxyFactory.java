package proxy_jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements InvocationHandler {
	public Object target;

	public void setTarget(Object target) {
		this.target = target;
	}

	public Object createProxy() {

		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		System.out.println("方法调用之前");
		Object result = method.invoke(target, args);
		System.out.println("方法调用之后");
		return result;
	}
}
