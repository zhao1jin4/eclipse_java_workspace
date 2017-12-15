package jdk8_new;


interface ParentInterface 
{
	default void objectMethodWithBody()  
	{
		System.out.println(" in ParentInterface  objectMethodWithBody");
	}
}


@FunctionalInterface  //如果接口只有一个未实现的方法(包括继承的)默认就是函数式接口,如果加这个,最多只能有一个未实现的接口
interface InterfaceJDK8
{
	static String CHAR_SET="UTF-8"; //接口的static变量会子类被继续下来
	
	default void objectMethodWithBody() //接口带方法实现,如对象方法必须以default声明
	{
		System.out.println(" in InterfaceJDK8  objectMethodWithBody");
	}
	
	static void staticMethodWithBody() //接口带方法实现,static方法不会被继续下来
	{
		System.out.println(" in  staticMethodWithBody");
	}
	void abstractMethod();
}
interface InterfaceJDK8Child extends InterfaceJDK8,ParentInterface 
{
	@Override
	default void objectMethodWithBody() {
		InterfaceJDK8.super.objectMethodWithBody();//super用法
	}
}
 

public class InterfaceBody {
	public static void main(String[] args) throws Exception
	{
		InterfaceJDK8.staticMethodWithBody();
		System.out.println(InterfaceJDK8Child.CHAR_SET);
	}

}
