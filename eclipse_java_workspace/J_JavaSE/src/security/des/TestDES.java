package security.des;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class TestDES
{
	protected static char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	//CIP
	public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("���Ȳ���ż��");
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
	      throw new IllegalArgumentException("���Ȳ���ż��");
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
	public static void main(String[] args)throws Exception
	{
		//decodeAsDESede();
		//encodeAsDESede();
		
//		Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
//		Security.getProviders();
		//-------������� Key ��Կ
//		SecureRandom random = new SecureRandom();
//		KeyGenerator keyGenerator = KeyGenerator.getInstance ("DES" );
//		keyGenerator.init (random);
//		SecretKey key = keyGenerator.generateKey();				
//		//------������    ���� Key ��Կ
		DESKeySpec keySpec = new DESKeySpec ("12345678".getBytes());//һ��Ҫ�ǳ���8
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES" );
		SecretKey key = keyFactory.generateSecret(keySpec);
		//------������    ���� Key ��Կ
		//Key key = new javax.crypto.spec.SecretKeySpec("12345678".getBytes(), "DES");// ������Կ,һ��Ҫ�ǳ���8��byte[]
		//�ӿ� SecretKey�̳��Խӿ�Key
		//--------�����ǳɹ�ʾ��
		Cipher en_cipher = Cipher.getInstance( "DES" );
		en_cipher.init( Cipher.ENCRYPT_MODE, key ); //����
		//��ʹ����SecureRandomʱ,�����ط���,����������SecureRandom
		byte[] en_byte=en_cipher.doFinal("helloWorld".getBytes());

		System.out.println("����ǰ:"+byteToHexString(en_byte));

		Cipher de_cipher = Cipher.getInstance( "DES" );
		de_cipher.init( Cipher.DECRYPT_MODE, key ); //����
		byte[] de_byte=de_cipher.doFinal(en_byte);
		System.out.println("�����:"+new String(de_byte));
	}

	
	// tool
	private static String byteToHexString(byte bytes[]) 
	{
		//char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len=bytes.length;
		StringBuffer buffer = new StringBuffer(2 * len);
		for (int i = 0;i < len; i++) 
		{	
			String c0 =Integer.toHexString( (bytes[i] & 0xf0) >> 4 );  //>>>���޷�������,��0
			String c1 =Integer.toHexString(  bytes[i] & 0x0f       );
			//������
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
			   String two = new String(hexBuf, i, 2);//ÿ��ȡ����
			   resultBuf[i / 2] = (byte) Integer.parseInt(two, 16);
		  }
		 //int len = hex.length();//���ܻ�������   /2 �Ǵ����
		  return resultBuf;
	}
}
