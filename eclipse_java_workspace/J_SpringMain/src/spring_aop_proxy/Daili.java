package spring_aop_proxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
public class Daili implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("mehtod before invoke;");
		Object o=invocation.proceed();
		System.out.println("mehtod after invoke;");
		return o;
	}
	

}
