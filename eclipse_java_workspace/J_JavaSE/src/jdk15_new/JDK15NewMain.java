package jdk15_new;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.EdECPoint;
import java.security.spec.EdECPublicKeySpec;
import java.security.spec.NamedParameterSpec;

import java.security.KeyPairGenerator;

public class JDK15NewMain {

	public static void main(String[] args) throws Exception {
		//Text Block ,���Է���дSQL
		 String html = """
			  <html>
				  <body>
					  <p>Hello, world</p>
				  </body>
			  </html>
			   """;

		//--preview �ؼ���record,���Զ���дequals,hashCode,toString
		  record Point(int x, int y) { }

		  
		//---preview Pattern Matching 
		  String obj="1,a";
		  if (obj instanceof String s) {
		  	//��ǰҪ��   String s = (String) obj; 
		  	s.split(",");
		  }
		  
		//���㷨
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
		KeyPair kp = kpg.generateKeyPair();
		Signature sig = Signature.getInstance("Ed25519");
		sig.initSign(kp.getPrivate());
		//sig.update(msg);
		byte[] s = sig.sign();

		// example: use KeyFactory to contruct a public key
		KeyFactory kf = KeyFactory.getInstance("EdDSA");
		NamedParameterSpec paramSpec = new NamedParameterSpec("Ed25519");
		boolean xOdd = true;
		BigInteger y1 =   BigInteger.valueOf(2);
		EdECPublicKeySpec pubSpec = new EdECPublicKeySpec(paramSpec, new EdECPoint(xOdd, y1));
		PublicKey pubKey = kf.generatePublic(pubSpec);
				
		

		//java.lang.invoke.MethodType::descriptorString;

//		ZGC ����������,ֻ��XX:+UseZGC ����
//		Shenandoah GC�ռ���������������������ͣʱ��, ֻ��-XX:+UseShenandoahGC ����

//		ɾ��  rmic ���� 
//		ɾ��  Nashorn  JavaScript Engine
	}

}
