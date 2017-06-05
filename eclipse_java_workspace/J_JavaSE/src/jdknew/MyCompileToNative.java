package jdknew;
public class MyCompileToNative 
{
	public static void main(String[] args) throws Exception 
	{
		 //------------------------ 
		boolean isOK=Compiler.compileClass(MyJFrame.class);
		 //Java-to-native-code compilers 
		if(isOK)
			System.out.println("±‡“ÎOK");
		else
			System.out.println("±‡“ÎFail");
		
	}

}
