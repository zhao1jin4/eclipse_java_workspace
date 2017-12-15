package security.des;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil 
{
	 
	private static Key getRandomKey()
	{
		SecretKey key=null;
		try
		{
			SecureRandom random = new SecureRandom();
			SecureRandom randomx = SecureRandom.getInstance("SHA1PRNG");
			KeyGenerator keyGenerator = KeyGenerator.getInstance ("DES" );
			keyGenerator.init (randomx);
			key = keyGenerator.generateKey();	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return key;
	}
	private static Key getKey(String str) throws Exception
	{
		if(str==null)
			return null;
		
		if(str.length()!=8)
			throw new Exception("the key string length must be 8.");
		
		SecretKey key=null;
		try
		{
			DESKeySpec keySpec = new DESKeySpec (str.getBytes());//一定要是长度8
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES" );
			key = keyFactory.generateSecret(keySpec);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return key;
	}
	private static Key getKey2(String str) throws Exception
	{
		if(str==null)
			return null;
		
		if(str.length()!=8)
			throw new Exception("the key string length must be 8.");
		
		SecretKey key=null;
		try
		{
			key = new javax.crypto.spec.SecretKeySpec(str.getBytes(), "DES");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return key;
	}
	
	
	
	public static String encryptString(String  str,String key)
	{
		try {
//			Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
//			Security.getProviders();
			
			if(str==null)
				return null;
			
			Cipher en_cipher = Cipher.getInstance( "DES" );//RC4
			en_cipher.init( Cipher.ENCRYPT_MODE, getKey(key)); 
			byte[] en_byte=en_cipher.doFinal(str.getBytes());
			return byteToHexString(en_byte);

			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public static String decryptString(String  str,String key)
	{
		if(str==null)
			return null;
		
		String result=null;
		try 
		{
			Cipher de_cipher = Cipher.getInstance( "DES" );//RC4
			de_cipher.init( Cipher.DECRYPT_MODE, getKey(key) ); //解密
			byte[] de_byte=de_cipher.doFinal(hexStringToBtye(str));
			result=new String(de_byte);	
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
			return result;
		}
	
	
	
	// tool
	private static String byteToHexString(byte bytes[]) 
	{
		//char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len=bytes.length;
		StringBuffer buffer = new StringBuffer(2 * len);
		for (int i = 0;i < len; i++) 
		{	
			String c0 =Integer.toHexString( (bytes[i] & 0xf0) >> 4 );  //>>>是无符号右移,左补0
			String c1 =Integer.toHexString(  bytes[i] & 0x0f       );
			//或者用
			//char c0 = HEX[ (bytes[i] & 0xf0) >> 4 ];
			//char c1 = HEX[  bytes[i] &  0x0f ];
			buffer.append(c0);
			buffer.append(c1);
		}
		return buffer.toString();
	}
	
	 public static byte[] hexStringToBtye(String hex) 
	 {
		  byte[] hexBuf = hex.getBytes();
		  int len = hexBuf.length;
		  byte[] resultBuf = new byte[len / 2];
		  for (int i = 0; i < len; i = i + 2) 
		  {
			   String two = new String(hexBuf, i, 2);//每次取两个
			   resultBuf[i / 2] = (byte) Integer.parseInt(two, 16);
		  }
		 //int len = hex.length();//可能会是奇数   /2 是错误的
		  return resultBuf;
	}
	
	 
	 
	 
	public static void main(String[] args)
	{
		getRandomKey();
		
		String encode=DESUtil.encryptString("lzj", "44332211");
		String expect="10d7974ad819cf52";
		
		System.out.println(expect.equals(encode));
		
		
		String decode=DESUtil.decryptString("10d7974ad819cf52", "44332211");
		System.out.println(decode.equals("lzj"));
	}
}
