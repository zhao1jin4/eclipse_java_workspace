package testng.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
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
public class MyTransformer implements IAnnotationTransformer {
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		if (testMethod!=null && "mongodb".equals(testMethod.getName())) {
			annotation.setInvocationCount(5);
		}
	}
}
//�粻��xml������listenerҲ��������ʱָ�� java org.testng.TestNG -listener testng.MyTransformer bin/testng/testng_group.xml
