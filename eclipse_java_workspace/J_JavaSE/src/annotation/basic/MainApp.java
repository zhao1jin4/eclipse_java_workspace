package annotation.basic;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@MyInerface({"111","in class"})//如果属性名是value,可以不加属性名,数组多个加{},一个可以不加{}
public class MainApp {
	
	@MyInerface({"234","in field"})
	private int id;
	
	
	@MyInerface(name={"张"}, value={"in method"},color=Color.bule)
	@Deprecated
	@SuppressWarnings({ "unused", "rawtypes" })//它是 RetentionPolicy.SOURCE 所以读不到
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
			//如果是 RetentionPolicy.CLASS是读不到的,只有当 RetentionPolicy.RUNTIME是可以的
			{
				MyInerface anno=testMethod.getAnnotation(MyInerface.class);
				String[] names=anno.name();
				System.out.println("读到Annotationr的信息names:"+names[0]);
				Color color=anno.color();
				System.out.println("读到Annotationr的信息 color:"+color);
				
				
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
