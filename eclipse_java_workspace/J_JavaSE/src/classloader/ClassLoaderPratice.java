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
	public Class<?> findClass(String name) //��дClassLoader�ķ��� ,loadClass�������Զ������������
	{
		 byte[] b = loadClassData(name);//����ָ��Ŀ¼,...
	     return defineClass(name, b, 0, b.length);//����ClassLoader����
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
 * ClassLoaderA��װ������A������ʵ��A1����ClassLoaderB��Ҳװ������A�Ķ���ʵ��A2���߼��Ͻ�A1=A2����������A1��A2�����ڲ�ͬ��ClassLoader������ʵ��������ȫ��ͬ�ģ����A�ж�����һ����̬����c����c�ڲ�ͬ��ClassLoader�е�ֵ�ǲ�ͬ�ġ�
 */
	//ϵͳ��װ��������ͨ��ClassLoader.getSystemClassLoader() ������
	public static void main(String[] args)throws Exception 
	{
		//newInstance()������Ĭ�Ϲ�����
		
		MyObject test=new MyObject();
        System.out.println(test);
        
        
		Class c  =  Class.forName("java.lang.Object");
		 ClassLoader  cl  =  c.getClassLoader();//null,bootstrapװ�ص� 
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
        //��ָ��Parent MyClassLoader��findClass���ᱻ����
        Class clazz1=  my1.loadClass("quiz.MyObject");
        
    	System.out.println( My1 == clazz1); //true
        
        
        //  ��/������Ŀ¼,������.jar
		URLClassLoader  urlLoader=  URLClassLoader.newInstance(new URL[]{new URL("file:///d:/temp/")},null);//�ڶ���������parent,
		ClassLoader parent=urlLoader.getParent();
		System.out.println(parent);//��null,����ʱָ����,bootstrap������
		Class My2=urlLoader.loadClass("quiz.MyObject");
		
		System.out.println( My1 == My2); //false
		
		Object o2  =  My2.newInstance();
		Field f2 = My2.getField("N");
		int n2  =  f2.getInt(o2);
		
	}

}
