package mypackage.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPublicKey;

import org.apache.commons.codec.binary.Base64;

public class ReverseSerialize {

	public static void main(String[] args) throws Exception {
		//返序列化
		Signature signature=Signature.getInstance("SHA1withDSA");
		
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");   
        // 初始化随机产生器   
        SecureRandom secureRandom = new SecureRandom();   
        secureRandom.setSeed("651332".getBytes());   
        keygen.initialize(1024, secureRandom);   
        KeyPair keys = keygen.genKeyPair();   
        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
        System.out.println("写入文件前的:"+Base64.encodeBase64String(publicKey.getEncoded()));
        
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream ("c:/temp/public.key"));
        out.writeObject(publicKey);
        out.close();
        
        
        DSAPublicKey  readPublicKey;
        ObjectInputStream in=new ObjectInputStream (new FileInputStream("c:/temp/public.key"));
        readPublicKey=(DSAPublicKey)in.readObject();
        System.out.println("文件中读出的:"+Base64.encodeBase64String(readPublicKey.getEncoded()));
        in.close();
				
	}

}
