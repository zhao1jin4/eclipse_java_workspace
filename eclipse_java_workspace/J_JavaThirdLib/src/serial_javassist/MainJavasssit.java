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
		printExecuteTimeProxy();//未成功
	}
	public static void helloProxy() throws Exception {
		
		//可以没有接口
		ClassPool pool=new ClassPool(true);
		pool.insertClassPath(new LoaderClassPath(MainJavasssit.class.getClassLoader()));

		CtClass targetClass=pool.makeClass("my.javassist.Hello");//动态代理生成新的class，比cglib慢
		targetClass.addInterface(pool.get(IHello.class.getName()));

		CtClass returnType=pool.get(void.class.getName());
		String mname="sayHello";
		CtClass[] parameters=new CtClass[] {pool.get(String.class.getName())};
		CtMethod method=new CtMethod(returnType, mname, parameters, targetClass);
		String src="{ System.out.println(\"hello \"+$1); }"; //$1是第一个参数
		method.setBody(src);
		targetClass.addMethod(method);
		Class<IHello> clazz=targetClass.toClass();
		IHello  hello=clazz.newInstance();
		hello.sayHello("王");
		  
	}
	public static void printExecuteTimeProxy() throws Exception {
		
	 
		ClassPool pool=new ClassPool(true);
		CtClass targetClass=pool.get("my.javassist.StringPerformance"); 
		CtMethod method=targetClass.getDeclaredMethod("appendStr");
		CtMethod newMethod=CtNewMethod.copy(method,method.getName()+"_proxy", targetClass, null);
		targetClass.addMethod(newMethod);
//		newMethod.insertBefore(src);
//		newMethod.insertAfter(src); //不能仿问before的变量 
		
		String src="{ "
				+ "long begin=System.currentTimeMillis();"
				+ "Object result="+method.getName()+"_proxy($$);"// $$是所有的参数列表
				+ "long end=System.currentTimeMillis();"
				+ "System.out.println(end-begin);" 
				+ "return ($r)result;"//$r是返回类型
				+ "}";
		newMethod.setBody(src); 
		targetClass.toClass(); //载入classloader,之前不能已经 加载StringPerformance类
		StringPerformance p=new StringPerformance();
		p.appendStr(200); //没有调到新的方法
	}
	
	public interface IHello{
		public void sayHello(String name);
	}
}
