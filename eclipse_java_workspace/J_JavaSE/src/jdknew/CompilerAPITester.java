package jdknew;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


public class CompilerAPITester 
{
	//权限控制.   不可访问 网络,DB,文件(用policy文件吗)
	
    private static String JAVA_SOURCE_FILE = "DynamicObject.java";
    private static String JAVA_CLASS_FILE = "DynamicObject.class";
    private static String JAVA_CLASS_NAME = "DynamicObject";
    private static String PACKAGE="jdknew";
    public static void main(String[] args) 
    {
    	//1.6功能
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        
        generateJavaClass();
        
        try {
            Iterable<? extends JavaFileObject> sourcefiles = fileManager.getJavaFileObjects(JAVA_SOURCE_FILE);
            compiler.getTask(null, fileManager, null, null, null, sourcefiles).call();
            fileManager.close();
            if(System.getProperty("os.name").toLowerCase().contains("windows"))//not check windows
            	Runtime.getRuntime().exec("cmd /c copy "+JAVA_CLASS_FILE+" bin/"+PACKAGE);
            else
            	Runtime.getRuntime().exec("cp -r "+JAVA_CLASS_FILE+" bin/"+PACKAGE);
//            Class x=Thread.currentThread().getContextClassLoader().loadClass(PACKAGE+"."+JAVA_CLASS_NAME);
            Class.forName(PACKAGE+"."+JAVA_CLASS_NAME);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void generateJavaClass(){
        try {
            FileWriter fw = new FileWriter(JAVA_SOURCE_FILE);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("package "+PACKAGE+";");//和标准 java一样，可不加pakcage
            bw.newLine();
            bw.write("public class "+JAVA_CLASS_NAME+"{");
            bw.newLine();
            bw.write("		public "+JAVA_CLASS_NAME+"(){" );
            bw.newLine();
            bw.write("			System.out.println(\"In the constructor of DynamicObject\");");
            bw.newLine();
            bw.write("		}");
            bw.newLine();
            bw.write("	}");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
