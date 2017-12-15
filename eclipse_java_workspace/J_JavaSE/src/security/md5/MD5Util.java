package security.md5;


import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.MyByteArrayUtil;

public class MD5Util 
{
	protected static char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static String encryptFile(File file)
	{
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
			
			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 10];
			int len = 0;
			while ((len = in.read(buffer)) > 0)
			{
				messagedigest.update(buffer, 0, len);
			}
			in.close();

		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		String res=MyByteArrayUtil.byteToHexString(messagedigest.digest()); 
		return res;
	}

	public static String getMD5String(String s) 
	{
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) 
	{
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		messagedigest.update(bytes);
		String res= MyByteArrayUtil.byteToHexString(messagedigest.digest());
		return res;
	}


	public static void main(String[] args)
	{ 
			 String file="C:/My/soft_ware/___java/apache-cxf-3.0.3.tar.gz"; 
			 String res=MD5Util.encryptFile(new File(file));
			 System.out.println(res);
		 
	}
}



