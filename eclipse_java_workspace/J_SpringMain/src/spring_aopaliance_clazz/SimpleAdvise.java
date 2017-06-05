package spring_aopaliance_clazz;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class SimpleAdvise implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("before");
        Object retVal=invocation.proceed();
        System.out.println("after");
        return retVal;
    }
}
