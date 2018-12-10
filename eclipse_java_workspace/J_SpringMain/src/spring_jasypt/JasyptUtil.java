package spring_jasypt;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptUtil {
	//可配置文件加密 密码 ENC(xxx)
	static String KEY="theKey";
		
	//PBEWithMD5AndDES
	public static String  encode(String thePass) {
		BasicTextEncryptor encryptor=new BasicTextEncryptor();
		encryptor.setPassword(KEY);
		String enc= encryptor.encrypt(thePass);
		return enc;
	}
	//PBEWithMD5AndDES
	public static String  decode(String enc) {
		
//		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//	    encryptor.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);//PBEWithMD5AndDES
		
		BasicTextEncryptor encryptor=new BasicTextEncryptor();
		encryptor.setPassword(KEY);
		String dec= encryptor.decrypt(enc);
		return dec;
	}
	
	public static void main(String[] args) {
		
		String enc=encode("thePass");
		System.out.println("enc="+enc);//DaarVCtqChPzfrYGDc/IYA==
		String dec=decode(enc);
		System.out.println("dec="+dec); 
	}
}
