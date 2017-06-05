package proxy_cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

public class AOPInstrumenter implements MethodInterceptor//cglib
{
	private Enhancer enhancer = new Enhancer();//cglib
  
	Class myc=null;
	public Object getInstrumentedClass(Class clz) 
	{
		myc=clz;
		enhancer.setSuperclass(clz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	public Object intercept(Object o, Method method, Object[] args,
			MethodProxy proxy) throws Throwable
	{
		System.out.println("在方法前拦截:" + method.getName());
		
		Object result = proxy.invokeSuper(o, args);//OK
		//Object result = method.invoke(myc.newInstance(), args);//OK
		//Object result = proxy.invoke(myc.newInstance(), args);//OK
		
		return result;
	}

}
