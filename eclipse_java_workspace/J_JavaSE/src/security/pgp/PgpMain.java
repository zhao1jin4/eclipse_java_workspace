package security.pgp;


import java.io.File;

public class PgpMain 
{
	public static String encryptFile(File file)
	{
		return null;
	}
	public static void main(String[] args)
	{ 
			 String file="C:/My/soft_ware/___java\\apache-cxf-3.0.2.zip"; 
			 String res=PgpMain.encryptFile(new File(file));
			 System.out.println(res);
		 
	}
}



