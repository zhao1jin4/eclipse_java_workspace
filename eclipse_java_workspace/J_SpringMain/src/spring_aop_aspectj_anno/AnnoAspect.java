package spring_aop_aspectj_anno;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class AnnoAspect 
{	
	@Pointcut("@annotation(spring_aop_aspectj_anno.Caches)")  
    public void setCached(){}
	
	@Around("setCached()")//OK
	//@Around("@annotation(spring_aop_aspectj_anno.Caches)")//OK
	public Object aroundMethod(ProceedingJoinPoint point) 
	{
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Caches caches=method.getAnnotation(Caches.class);
		Object result = null;  
		if(StringUtils.isNotBlank(caches.prefixKey()))
		 {
			  System.out.println("config prefixKey="+caches.prefixKey());
		 }
		 try 
		 {
		 	System.out.println("before @Caches");
			result = point.proceed();
			System.out.println("after @Caches");
		} catch (Throwable e) { 
			e.printStackTrace();
		}   
        return result;  
    }  
}
