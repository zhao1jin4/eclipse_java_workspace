package security.dsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class DSATest {
	public static final String KEY_ALGORITHM = "DSA";
	public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";
	/**
	 * DSA密钥长度，RSA算法的默认密钥长度是1024 密钥长度必须是64的倍数，在512到1024位之间
	 * */
	private static final int KEY_SIZE = 1024;

	public static void main(String[] args) throws Exception {

		// 生成Key
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed("651332".getBytes());
		keyPairGenerator.initialize(KEY_SIZE, secureRandom);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();

		System.out.println("公钥：\n"+ Base64.encodeBase64String(publicKey.getEncoded()));// apache的codec
		System.out.println("私钥：\n"+ Base64.encodeBase64String(privateKey.getEncoded()));
		String str = "这是要加密的密文abc123";
		System.out.println("原文:" + str);

		//Private生成签名 
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded()); // 使用生成的Key
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec); // 内部的Key
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(str.getBytes());
		byte[] signeData = signature.sign();
		System.out.println("产生签名：" + Base64.encodeBase64String(signeData));

		//Public验证签名
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey.getEncoded());// 使用生成的Key
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec); // 内部的Key
		signature.initVerify(pubKey);
		signature.update(str.getBytes());
		boolean result = signature.verify(signeData);
		System.out.println("验证结果:" + result);
		
	}

}