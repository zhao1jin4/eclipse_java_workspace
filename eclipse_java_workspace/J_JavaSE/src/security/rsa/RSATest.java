package security.rsa;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

public class RSATest {

	public static void main(String[] args) throws Exception 
	{

		//privateEncPublicDec();
		privateDecPublicEnc();
	}
	public static void privateEncPublicDec() throws Exception 
	{

		String str = "encryptText测试123";
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();

		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		//加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);//或publicKey
		byte[]encryptData = cipher.doFinal(str.getBytes());
		
		//解密
		//Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);//或privateKey
		byte[]decryptData =cipher.doFinal(encryptData);
		System.out.println("解密结果:" + new String(decryptData));
	}
	//正常用是公钥加密，私钥解密
	public static void privateDecPublicEnc() throws Exception 
	{

		String str = "encryptText测试123";
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();

		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		//加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey); 
		byte[]encryptData = cipher.doFinal(str.getBytes());
		
		//解密
		//Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey); 
		byte[]decryptData =cipher.doFinal(encryptData);
		System.out.println("解密结果:" + new String(decryptData));
	}
}
