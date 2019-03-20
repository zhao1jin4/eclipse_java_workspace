package testng.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
/**
 *  所有的listener可以是 
 *  IAnnotationTransformer
 *  IAnnotationTransformer2
 *  IHookable
 *  IInvokedMethodListener
 *  IMethodInterceptor
 *  IReporter
 *  ISuiteListener
 *  ITestListener 
 */
public class MyTransformer implements IAnnotationTransformer {
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		if (testMethod!=null && "mongodb".equals(testMethod.getName())) {
			annotation.setInvocationCount(5);
		}
	}
}
//如不在xml中配置listener也可在启动时指定 java org.testng.TestNG -listener testng.MyTransformer bin/testng/testng_group.xml
