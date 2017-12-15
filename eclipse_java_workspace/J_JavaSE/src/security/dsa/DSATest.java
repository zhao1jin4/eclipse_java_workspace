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
	 * DSA��Կ���ȣ�RSA�㷨��Ĭ����Կ������1024 ��Կ���ȱ�����64�ı�������512��1024λ֮��
	 * */
	private static final int KEY_SIZE = 1024;

	public static void main(String[] args) throws Exception {

		// ����Key
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed("651332".getBytes());
		keyPairGenerator.initialize(KEY_SIZE, secureRandom);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();

		System.out.println("��Կ��\n"+ Base64.encodeBase64String(publicKey.getEncoded()));// apache��codec
		System.out.println("˽Կ��\n"+ Base64.encodeBase64String(privateKey.getEncoded()));
		String str = "����Ҫ���ܵ�����abc123";
		System.out.println("ԭ��:" + str);

		//Private����ǩ�� 
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded()); // ʹ�����ɵ�Key
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec); // �ڲ���Key
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(str.getBytes());
		byte[] signeData = signature.sign();
		System.out.println("����ǩ����" + Base64.encodeBase64String(signeData));

		//Public��֤ǩ��
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey.getEncoded());// ʹ�����ɵ�Key
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec); // �ڲ���Key
		signature.initVerify(pubKey);
		signature.update(str.getBytes());
		boolean result = signature.verify(signeData);
		System.out.println("��֤���:" + result);
		
	}

}