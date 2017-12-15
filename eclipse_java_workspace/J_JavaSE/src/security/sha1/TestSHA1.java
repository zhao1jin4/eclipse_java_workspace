package security.sha1;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;

import util.MyByteArrayUtil;


public class TestSHA1
{

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
	    '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


	
	public static String Sha1EncryptFile(String file) 
	{
		FileInputStream fromFile=null;
		String hexStr=null;
		 try
		{
			 fromFile=new FileInputStream(file);
				
			 MessageDigest digest=MessageDigest.getInstance("SHA-1");//SHA-1 在JDK9中要过时,因被google破解
//			 MessageDigest.getInstance("sha-1");
//			 MessageDigest.getInstance("Sha-1"); //是一样的。
			
			byte[] buffer=new byte[1024];
			int len=0;
			while((len=fromFile.read(buffer))!=-1)
			{
				digest.update(buffer,0,len);
			}
			hexStr= MyByteArrayUtil.byteToHexString(digest.digest()); //
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try {
				fromFile.close();
			} catch (IOException e) { }
		}
		 return hexStr;
	}
	public static void main(String[] args)
	{
		String file="C:/My/soft_ware/___java/apache-cxf-3.0.3.tar.gz";
		String hexStr=Sha1EncryptFile(file);
		System.out.println(hexStr);
		

		Provider[] pros=Security.getProviders();
		for(int i=0;i<pros.length;i++)
			System.out.println(pros[i].getInfo());
	}
}
