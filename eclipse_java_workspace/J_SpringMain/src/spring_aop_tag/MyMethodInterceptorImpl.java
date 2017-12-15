 
package spring_aop_tag;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
 
public class MyMethodInterceptorImpl implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {        
		long start = System.currentTimeMillis();
		boolean isSuccess = false;
		try{
			Object rval = invocation.proceed();
			isSuccess =true;
			return rval;
		}finally{
			StringBuffer message = new StringBuffer();
			message
				.append(invocation.getMethod().getDeclaringClass().getSimpleName()).append(".").append(invocation.getMethod().getName()).append(";")
				.append(System.currentTimeMillis()-start).append(";").append(";");		
			if(isSuccess){
				message.append("Y").append(";");
			}else{
				message.append("N").append(";");
			}			
			message.append(";").append(";").append(";");
			System.out.println(message.toString());
		}
	}
	
}
