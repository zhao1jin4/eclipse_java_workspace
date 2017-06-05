package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
class MyClassLoader extends ClassLoader
{
	public Class<?> findClass(String name) //重写ClassLoader的方法 ,loadClass方法会自动调用这个方法
	{
		 byte[] b = loadClassData(name);//从特指的目录,...
	     return defineClass(name, b, 0, b.length);//调用ClassLoader方法
    }

    private byte[] loadClassData(String name)  
    {
    	String path="d:/temp";
    	
    	name=name.substring(0,name.indexOf(".class"));
    	String pack=name.replace(".", File.separator);
    	if(pack.contains("$"));
    		pack.replace("$", File.separator);
    	ByteArrayOutputStream out=new ByteArrayOutputStream();
    	FileInputStream input=null;
    	try{
        	
    		input=new FileInputStream(path+pack);
        	byte[] buffer=new byte[1024];
        	int readed=0;
        	while((readed=input.read(buffer))!=-1)
    		{
        		out.write(buffer,0,readed);
    		}
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally{
	    	try {
				out.close();
				input.close();
			} catch (IOException e) {}
	    }
    	
    	return out.toByteArray();
    }

	
}
public class ClassLoaderPratice {
/*
 * ClassLoaderA，装载了类A的类型实例A1，而ClassLoaderB，也装载了类A的对象实例A2。逻辑上讲A1=A2，但是由于A1和A2来自于不同的ClassLoader，它们实际上是完全不同的，如果A中定义了一个静态变量c，则c在不同的ClassLoader中的值是不同的。
 */
	//系统类装载器可以通过ClassLoader.getSystemClassLoader() 方法得
	public static void main(String[] args)throws Exception 
	{
		//newInstance()必须有默认构造器
		
		MyObject test=new MyObject();
        System.out.println(test);
        
        
		Class c  =  Class.forName("java.lang.Object");
		 ClassLoader  cl  =  c.getClassLoader();//null,bootstrap装载的 
		 System.out.println( " java.lang.Object's loader is  "   +  cl);
		 
		 ClassLoader threadLoader= Thread.currentThread().getContextClassLoader();
		 
		 ClassLoader  systemLoader  =  ClassLoader.getSystemClassLoader();//sun.misc.Launcher$AppClassLoader@19821f
	     System.out.println(systemLoader);
	     
	     ClassLoader ext  =  systemLoader.getParent();//sun.misc.Launcher$ExtClassLoader@addbf1
         System.out.println(ext);
         
         ClassLoader boot  =  ext.getParent();
         System.out.println(boot);//null,bootstrap
         
         
        Class My1= systemLoader.loadClass("quiz.MyObject");
        Field f1 = My1.getField("N");
        Object o1  =  c.newInstance();
        int n1  =  f1.getInt(o1);
        f1.setInt(o1, 22);
        
        
        MyClassLoader my1=new MyClassLoader(); 
        ClassLoader p= my1.getParent();//sun.misc.Launcher$AppClassLoader@58644d46
        //不指定Parent MyClassLoader的findClass不会被调用
        Class clazz1=  my1.loadClass("quiz.MyObject");
        
    	System.out.println( My1 == clazz1); //true
        
        
        //  以/结束是目录,否则是.jar
		URLClassLoader  urlLoader=  URLClassLoader.newInstance(new URL[]{new URL("file:///d:/temp/")},null);//第二个参数是parent,
		ClassLoader parent=urlLoader.getParent();
		System.out.println(parent);//是null,构造时指定的,bootstrap加载器
		Class My2=urlLoader.loadClass("quiz.MyObject");
		
		System.out.println( My1 == My2); //false
		
		Object o2  =  My2.newInstance();
		Field f2 = My2.getField("N");
		int n2  =  f2.getInt(o2);
		
	}

}
