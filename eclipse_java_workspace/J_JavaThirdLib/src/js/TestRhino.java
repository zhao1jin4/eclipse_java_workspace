package js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/*
 * D:\Program\java_lib>java -cp js.jar org.mozilla.javascript.tools.shell.Main
Rhino 1.7 release 2 2009 03 22
js>
js>load('c:/temp/test.js');  
js>load('c:\\temp\\test.js');
js>add(1,2);
function add (a,b)   
{   
 return a+b;
}


java -cp js.jar org.mozilla.javascript.tools.debugger.Main   就可以看到调试器的界面了。


js文件运行的速度，可以把它编译为class文件：
java -cp js.jar org.mozilla.javascript.tools.jsc.Main c:/temp/test.js



*/
public class TestRhino
{

	public static void main(String[] args)throws Exception
	{
		//对表示式求值 rhino 
		Context cx = Context.enter();   
		try  
		{   
		  Scriptable scope = cx.initStandardObjects();   
		  String str = "9*(1+2);\n";
		  str+="//3+2;\n";
		  Object result = cx.evaluateString(scope, str, null, 1, null);   
		  double res = Context.toNumber(result);   
		  System.out.println(res);   
		}   
		finally  
		{   
		  Context.exit();   
		} 

/*
		var swingNames = JavaImporter();
		swingNames.importPackage(Packages.javax.swing);
		function createComponents() 
		{
		    with (swingNames) 
			{
				new JLabel("");
		//或者用完整java 包名

		System.getProperty("user.dir") //是当前目录


		Context cx = Context.enter()
		 Scriptable scope=cx.initStandardObjects();
		Object result = cx.evaluateString(scope, jsContent, filename, 1, null);
		
		
	*/	
	}
  
}
