package jdk8_new;


interface ParentInterface 
{
	default void objectMethodWithBody()  
	{
		System.out.println(" in ParentInterface  objectMethodWithBody");
	}
}


@FunctionalInterface  //����ӿ�ֻ��һ��δʵ�ֵķ���(�����̳е�)Ĭ�Ͼ��Ǻ���ʽ�ӿ�,��������,���ֻ����һ��δʵ�ֵĽӿ�
interface InterfaceJDK8
{
	static String CHAR_SET="UTF-8"; //�ӿڵ�static���������౻��������
	
	default void objectMethodWithBody() //�ӿڴ�����ʵ��,����󷽷�������default����
	{
		System.out.println(" in InterfaceJDK8  objectMethodWithBody");
	}
	
	static void staticMethodWithBody() //�ӿڴ�����ʵ��,static�������ᱻ��������
	{
		System.out.println(" in  staticMethodWithBody");
	}
	void abstractMethod();
}
interface InterfaceJDK8Child extends InterfaceJDK8,ParentInterface 
{
	@Override
	default void objectMethodWithBody() {
		InterfaceJDK8.super.objectMethodWithBody();//super�÷�
	}
}
 

public class InterfaceBody {
	public static void main(String[] args) throws Exception
	{
		InterfaceJDK8.staticMethodWithBody();
		System.out.println(InterfaceJDK8Child.CHAR_SET);
	}

}
