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
		
		//------------//@���ڸ���ķ�����Ҳ�ǿ��Ե�(��������า�Ǿ�û����),�ĵ���˵����������,��������Ч
		Method m=c.getMethod("doSomething", null);
		if(m.isAnnotationPresent(InheritedTest.class))
		{
			InheritedTest inheritedTest=m.getAnnotation(InheritedTest.class);
			String val=inheritedTest.value();
			System.out.println(val);
		}
		//------------ //@���ڽӿ����ǲ��ܱ��̳е�
		Class <MyChildImpl> my=MyChildImpl.class;
		if(my.isAnnotationPresent(InheritedTest.class))
		{
			InheritedTest inheritedTest=my.getAnnotation(InheritedTest.class);
			String val=inheritedTest.value();
			System.out.println(val);
		}
	}
}
