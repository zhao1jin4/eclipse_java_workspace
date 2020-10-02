package security.aes;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class MainAES {
	/** 
     * AES���� 
     * @param content �����ܵ����� 
     * @param encryptKey ������Կ 
     * @return ���ܺ��byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));  
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
          
        return cipher.doFinal(content.getBytes("UTF-8"));  
    }  
    /** 
     * AES���� 
     * @param encryptBytes �����ܵ�byte[] 
     * @param decryptKey ������Կ 
     * @return ���ܺ��String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));  
          
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes,"UTF-8");  
    }  
	public static void main(String[] args) throws Exception {
		//AES �� DES��ȡ����
		String content = "�Ұ���";  
        System.out.println("����ǰ��" + content);  
        String key = "123456";  
        System.out.println("������Կ�ͽ�����Կ��" + key);  

		Base64.Encoder base64Encoder=Base64.getEncoder();
		byte[] encoded=base64Encoder.encode(aesEncryptToBytes(content, key));
		System.out.println("���ܺ�"+new String(encoded));
		
		Base64.Decoder base64Decoder=Base64.getDecoder();
		byte[] decoded=base64Decoder.decode(encoded);
		System.out.println("���ܺ�" + aesDecryptByBytes(decoded, key));
 
           
	}

}
