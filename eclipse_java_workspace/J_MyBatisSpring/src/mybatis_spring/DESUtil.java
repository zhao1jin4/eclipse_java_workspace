package mybatis_spring;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
 

public class DESUtil {
	private static Key key;
	private static String KEY_STR = "theKeyPass";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";
	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY_STR.getBytes());
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
 		try {
			byte[] bytes = str.getBytes(CHARSETNAME);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			
			Base64.Encoder base64Encoder=Base64.getEncoder();
			byte[] encoded=base64Encoder.encode(doFinal);
			return new String(encoded);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		try {
			Base64.Decoder base64Decoder=Base64.getDecoder();
			byte[] bytes=base64Decoder.decode(str); 

			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
		System.out.println(getEncryptString("zh"));
		System.out.println(getEncryptString("123"));
	}
}