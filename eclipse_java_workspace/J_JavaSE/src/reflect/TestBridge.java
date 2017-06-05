package reflect;

import java.beans.Introspector;
import java.lang.reflect.Method;

public class TestBridge
{
	public static void main(String[] args) {
		Method[] method=B.class.getMethods();
		System.out.println(method[0].isBridge());//true
		//B�̳�abstract A,A��<ģ��>,Bʹ����A��<ģ��>,B���ɵ��ֽ������brige����,Ŀ����Ϊ�˿��Ը���A�еķ���
	
		String str=	 Introspector.decapitalize("OuterClazz.InnerClazz");//outerClazz.InnerClazz
		System.out.println(str);
		
		if(null instanceof String)//false
			System.out.println("null is instanceof Clazz");
		 else 
			System.out.println("null is not instanceof Clazz");
	}
}
abstract class A<T> {
    public abstract T method1(T arg);
    public abstract T method2();
}
 
class B extends A<String> {
    public String method1(String arg) {
       return arg;
    }
    public String method2() {
       return "abc";
    }
}
 
class C<T> extends A<T> {
    public T method1(T arg) {
       return arg;
    }
  
    public T method2() {
       return null;
    }
}


