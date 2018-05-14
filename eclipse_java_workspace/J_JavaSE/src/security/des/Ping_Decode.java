package security.des;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
 

public class Ping_Decode
{
	protected static char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	//CIP
	public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
	 public static String byte2hex(byte[] b) {
	        String hs = "";
	        String stmp = "";
	        for (int n = 0; b != null && n < b.length; n++) {
	            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	            if (stmp.length() == 1)
	                hs = hs + "0" + stmp;
	            else
	                hs = hs + stmp;
	        }
	        return hs.toUpperCase();
	    }
	 
	//HS
	 private static byte[] hexToByte(byte[] b)
	  {
	    if (b.length % 2 != 0) {
	      throw new IllegalArgumentException("长度不是偶数");
	    }
	    byte[] b2 = new byte[b.length / 2];
	    for (int n = 0; n < b.length; n += 2) {
	      String item = new String(b, n, 2);

	      b2[(n / 2)] = (byte)Integer.parseInt(item, 16);
	    }
	    b = (byte[])null;
	    return b2;
	  }
	  public static String byteToHex(byte[] b)
	  {
	    String hs = "";
	    String tmp = "";
	    for (int n = 0; n < b.length; n++)
	    {
	      tmp = Integer.toHexString(b[n] & 0xFF);
	      if (tmp.length() == 1)
	        hs = hs + "0" + tmp;
	      else {
	        hs = hs + tmp;
	      }
	    }
	    tmp = null;
	    return hs.toUpperCase();
	  }
	 //CIP
	public static void decodeAsDESede()throws Exception
	{
		
		  SecretKey deskey = new SecretKeySpec("Q_jDpaf_1a(iOX@P7g)_H8JR".getBytes(), "DESede");
          Cipher cipher = Cipher.getInstance("DESede");
          
          cipher.init(Cipher.DECRYPT_MODE, deskey);
         byte[] decodeByte=cipher.doFinal(hex2byte("3E70AD7CED689648697EB0EC479CD9BF0475AB271A17E59D".getBytes()));
         String decodeStr=new String(decodeByte,"UTF-8");
         System.out.println(decodeStr);
	}
	//CIP bankcard Encode 
	public static void encodeAsDESede()throws Exception
	{
		
		  SecretKey deskey = new SecretKeySpec("Q_jDpaf_1a(iOX@P7g)_H8JR".getBytes(), "DESede");
          Cipher cipher = Cipher.getInstance("DESede");
          
          cipher.init(Cipher.ENCRYPT_MODE, deskey);
         byte[] decodeByte=cipher.doFinal("1000010000000527".getBytes());
         System.out.println( byte2hex(decodeByte));
	}
	 //CIP
		public static void decodeAsDESede(String encStr)throws Exception
		{
			
			  SecretKey deskey = new SecretKeySpec("Q_jDpaf_1a(iOX@P7g)_H8JR".getBytes(), "DESede");
	          Cipher cipher = Cipher.getInstance("DESede");
	          
	          cipher.init(Cipher.DECRYPT_MODE, deskey);
	         byte[] decodeByte=cipher.doFinal(hex2byte(encStr.getBytes()));
	         String decodeStr=new String(decodeByte,"UTF-8");
	         System.out.println(decodeStr);
		}
	public static void main(String[] args)throws Exception
	{ 
		String myDBPayPass  ="C96FAECC8AA9C951F484187797C7C9DE";
		String myDBLoginPass="EB42C8FCC728605FF484187797C7C9DE";
		
		//convert方法
		//----
		String m="a3da0dd5e9589c86ba812ae3dcf3091b9f8f51e889f89fd55eb2de54c917d8b54261db1d2d7458eceafa0cb6e128d94afa329ea58663c167f86e62fae3b77cfca59801aa5561b45de16e16884d738a90bd9d23d76623503d0c70a9366db0e4d7c87400f52dc9c236cb4353dd180bdd64dd7e2c17baa35cf14b0a516f8e87b341";
	    String ve="57e8f42762ed11fe0f2ea22e632b5a25a1f4294414527a9ee6121dcb6ba109ba26858e241771270ce5fd86b8d01134944facbd059e17037b9f04cac2454efb154d48721dbc6fed1647167726da7faa7027d160253610de661ce259e37e592641073a17ee2c45d5782931c42eb9dd902d513eeb10cd6345988ed56f20371c9ee1";
	    BigInteger    modulus = new BigInteger(m, 16);
	    BigInteger  privateExponent=  new BigInteger(ve, 16);
	    
	    Provider jdField_if = new BouncyCastleProvider();
        Security.addProvider(jdField_if);
      
	    KeyFactory localKeyFactory = KeyFactory.getInstance("RSA", jdField_if);
      RSAPrivateKeySpec localRSAPrivateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent );
      RSAPrivateKey privateKey=  (RSAPrivateKey)localKeyFactory.generatePrivate(localRSAPrivateKeySpec);	
	  //---
       
      
     
      String paramString="theStringfromMobileWebParam";//公钥加密后的结果 
      byte[] arrayOfByte1 = Base64.getDecoder().decode(paramString );//Base64解
      
//      sun.misc.BASE64Decoder localBASE64Decoder = new sun.misc.BASE64Decoder();
//      byte[] arrayOfByte1 = localBASE64Decoder.decodeBuffer(paramString);//Base64解
    
      
      int i = Integer.parseInt(new String(arrayOfByte1, 0, 8).trim());//8个字节  表示报文长度
      byte[] arrayOfByte2 = new byte[i - 12];//12 其它报文头长
      System.arraycopy(arrayOfByte1, 20, arrayOfByte2, 0, i - 12);//20是12+8
      
      int j = Integer.parseInt(new String(arrayOfByte1, 8 + i, 8).trim());//第二部分报文长度8个字节(第二个),第一个8+i是前面的头长
      byte[] arrayOfByte3 = new byte[j];
      System.arraycopy(arrayOfByte1, 8 + i + 8, arrayOfByte3, 0, j);
      
      Cipher localCipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding",jdField_if ); 
      localCipher1.init(2, privateKey);
      byte[] arrayOfByte4 = new byte['']; //应该是128的长度???
      for (int k = 0; k < 128; k++)
        arrayOfByte4[(127 - k)] = arrayOfByte2[k];// 像反序
      
      byte[] arrayOfByte5 = localCipher1.doFinal(arrayOfByte4);
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(arrayOfByte5, "RC4");
      Cipher localCipher2 = Cipher.getInstance("RC4");
      localCipher2.init(2, localSecretKeySpec);
      localCipher2.update(new byte[512]);
      byte[] arrayOfByte6 = localCipher2.doFinal(arrayOfByte3);
      String res= new String(arrayOfByte6);//转换后的结果
     
      
	}
	   
}
