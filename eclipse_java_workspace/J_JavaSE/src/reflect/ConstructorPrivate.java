package reflect;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;


public class ConstructorPrivate {
	public static void main(String[] args) throws Exception {
		
		//Constructor<?> ctor11=Test.class.getConstructor();//ֻ�ܵõ�public��
		
		Constructor<?> ctor=Test.class.getDeclaredConstructor();
		
		if(Modifier.PROTECTED==ctor.getModifiers())
			System.out.println("protected");
		
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
				&& !ctor.isAccessible()) {
			ctor.setAccessible(true);//��ʾ����ʵ����private�Ĺ�����
		}
		Object o=ctor.newInstance();
		System.out.println(o);
	}

}
class Test
{
	private Test ()
	{
	}
	private Test (int a)
	{
	}
}