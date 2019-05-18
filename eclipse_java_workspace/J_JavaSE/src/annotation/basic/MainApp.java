package annotation.basic;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@MyInerface({"111","in class"})//�����������value,���Բ���������,��������{},һ�����Բ���{}
public class MainApp {
	
	@MyInerface({"234","in field"})
	private int id;
	
	
	@MyInerface(name={"��"}, value={"in method"},color=Color.bule)
	@Deprecated
	@SuppressWarnings({ "unused", "rawtypes" })//���� RetentionPolicy.SOURCE ���Զ�����
	public void test( @MyInerface("in param")String str)
	{
		Map a=new HashMap();
	
	}
	
	@MyInerface({"111","in constructor"})
	public MainApp()
	{
		java.util.Hashtable x;
		java.util.HashMap y;
	}
	public static void main(String[] args) throws Exception 
	{
		Long l;
			Class<MainApp> c=MainApp.class;
			Method testMethod=c.getMethod("test",String.class);
			if(testMethod.isAnnotationPresent(MyInerface.class))
			//����� RetentionPolicy.CLASS�Ƕ�������,ֻ�е� RetentionPolicy.RUNTIME�ǿ��Ե�
			{
				MyInerface anno=testMethod.getAnnotation(MyInerface.class);
				String[] names=anno.name();
				System.out.println("����Annotationr����Ϣnames:"+names[0]);
				Color color=anno.color();
				System.out.println("����Annotationr����Ϣ color:"+color);
				
				
				for (Annotation item : testMethod.getAnnotations())
				{
					System.out.println(item.annotationType());
					
				}
			}

	}

}


@MyInerface({"InterfaceA","in interface"})
interface  InterfaceA {
	
}
