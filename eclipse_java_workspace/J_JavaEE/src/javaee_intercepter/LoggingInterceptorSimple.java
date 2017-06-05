package javaee_intercepter;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@LoggingInterceptor
@Interceptor
public class LoggingInterceptorSimple {
    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        System.out.println("LOG: " + context.getMethod());
        return context.proceed();
    }
}
