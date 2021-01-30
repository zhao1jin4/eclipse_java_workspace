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
		//Text Block ,可以方便写SQL
		 String html = """
			  <html>
				  <body>
					  <p>Hello, world</p>
				  </body>
			  </html>
			   """;

		//--preview 关键字record,会自动重写equals,hashCode,toString
		  record Point(int x, int y) { }

		  
		//---preview Pattern Matching 
		  String obj="1,a";
		  if (obj instanceof String s) {
		  	//以前要用   String s = (String) obj; 
		  	s.split(",");
		  }
		  
		//新算法
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

//		ZGC 可用于生产,只用XX:+UseZGC 即可
//		Shenandoah GC收集器，可用于生产，低暂停时间, 只用-XX:+UseShenandoahGC 即可

//		删除  rmic 命令 
//		删除  Nashorn  JavaScript Engine
	}

}
