package annotation.inherited;

import java.lang.reflect.Method;

public class MainTest 
{
	public static void main(String[] args) throws Exception {
		Class <Child> c=Child.class;
		if(c.isAnnotationPresent(InheritedTest.class))
		{
			InheritedTest inheritedTest=c.getAnnotation(InheritedTest.class);
			String val=inheritedTest.value();
			System.out.println(val);
		}
		
		//------------//@放在父类的方法上也是可以的(如果被子类覆盖就没用了),文档上说除了在类上,其它都无效
		Method m=c.getMethod("doSomething", null);
		if(m.isAnnotationPresent(InheritedTest.class))
		{
			InheritedTest inheritedTest=m.getAnnotation(InheritedTest.class);
			String val=inheritedTest.value();
			System.out.println(val);
		}
		//------------ //@放在接口上是不能被继承的
		Class <MyChildImpl> my=MyChildImpl.class;
		if(my.isAnnotationPresent(InheritedTest.class))
		{
			InheritedTest inheritedTest=my.getAnnotation(InheritedTest.class);
			String val=inheritedTest.value();
			System.out.println(val);
		}
	}
}
