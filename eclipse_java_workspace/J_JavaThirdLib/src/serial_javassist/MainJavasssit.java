package serial_javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;

public class MainJavasssit {

	public static void main(String[] args) throws Exception {
//		helloProxy();
		printExecuteTimeProxy();//δ�ɹ�
	}
	public static void helloProxy() throws Exception {
		
		//����û�нӿ�
		ClassPool pool=new ClassPool(true);
		pool.insertClassPath(new LoaderClassPath(MainJavasssit.class.getClassLoader()));

		CtClass targetClass=pool.makeClass("my.javassist.Hello");//��̬���������µ�class����cglib��
		targetClass.addInterface(pool.get(IHello.class.getName()));

		CtClass returnType=pool.get(void.class.getName());
		String mname="sayHello";
		CtClass[] parameters=new CtClass[] {pool.get(String.class.getName())};
		CtMethod method=new CtMethod(returnType, mname, parameters, targetClass);
		String src="{ System.out.println(\"hello \"+$1); }"; //$1�ǵ�һ������
		method.setBody(src);
		targetClass.addMethod(method);
		Class<IHello> clazz=targetClass.toClass();
		IHello  hello=clazz.newInstance();
		hello.sayHello("��");
		  
	}
	public static void printExecuteTimeProxy() throws Exception {
		
	 
		ClassPool pool=new ClassPool(true);
		CtClass targetClass=pool.get("my.javassist.StringPerformance"); 
		CtMethod method=targetClass.getDeclaredMethod("appendStr");
		CtMethod newMethod=CtNewMethod.copy(method,method.getName()+"_proxy", targetClass, null);
		targetClass.addMethod(newMethod);
//		newMethod.insertBefore(src);
//		newMethod.insertAfter(src); //���ܷ���before�ı��� 
		
		String src="{ "
				+ "long begin=System.currentTimeMillis();"
				+ "Object result="+method.getName()+"_proxy($$);"// $$�����еĲ����б�
				+ "long end=System.currentTimeMillis();"
				+ "System.out.println(end-begin);" 
				+ "return ($r)result;"//$r�Ƿ�������
				+ "}";
		newMethod.setBody(src); 
		targetClass.toClass(); //����classloader,֮ǰ�����Ѿ� ����StringPerformance��
		StringPerformance p=new StringPerformance();
		p.appendStr(200); //û�е����µķ���
	}
	
	public interface IHello{
		public void sayHello(String name);
	}
}
