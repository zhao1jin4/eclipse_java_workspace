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


java -cp js.jar org.mozilla.javascript.tools.debugger.Main   �Ϳ��Կ����������Ľ����ˡ�


js�ļ����е��ٶȣ����԰�������Ϊclass�ļ���
java -cp js.jar org.mozilla.javascript.tools.jsc.Main c:/temp/test.js



*/
public class TestRhino
{

	public static void main(String[] args)throws Exception
	{
		//�Ա�ʾʽ��ֵ rhino 
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
		//����������java ����

		System.getProperty("user.dir") //�ǵ�ǰĿ¼


		Context cx = Context.enter()
		 Scriptable scope=cx.initStandardObjects();
		Object result = cx.evaluateString(scope, jsContent, filename, 1, null);
		
		
	*/	
	}
  
}
