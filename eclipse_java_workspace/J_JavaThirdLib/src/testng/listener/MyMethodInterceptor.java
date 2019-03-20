package testng.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;
/**
 *  ���е�listener������ 
 *  IAnnotationTransformer
 *  IAnnotationTransformer2
 *  IHookable
 *  IInvokedMethodListener
 *  IMethodInterceptor
 *  IReporter
 *  ISuiteListener
 *  ITestListener 
 */
public class MyMethodInterceptor implements IMethodInterceptor {
	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		List<IMethodInstance> result = new ArrayList<IMethodInstance>();
		for (IMethodInstance m : methods) 
		{
			Test test = m.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
			if(test ==null)
			{
				test=m.getClass().getAnnotation(Test.class);
				if(test==null)
				{
					result.add(m);
					continue;
				}
			}
			Set<String> groups = new HashSet<String>();
			for (String group : test.groups()) {
				groups.add(group);
			}
			if (groups.contains("fast")) {//һ�������ĸ���ִ��
				result.add(0, m);
			} else {
				result.add(m);
			}
		}
		return result;
	}
}
