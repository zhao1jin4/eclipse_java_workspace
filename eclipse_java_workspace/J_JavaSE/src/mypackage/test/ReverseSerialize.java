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
		//�����л�
		Signature signature=Signature.getInstance("SHA1withDSA");
		
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");   
        // ��ʼ�����������   
        SecureRandom secureRandom = new SecureRandom();   
        secureRandom.setSeed("651332".getBytes());   
        keygen.initialize(1024, secureRandom);   
        KeyPair keys = keygen.genKeyPair();   
        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
        System.out.println("д���ļ�ǰ��:"+Base64.encodeBase64String(publicKey.getEncoded()));
        
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream ("c:/temp/public.key"));
        out.writeObject(publicKey);
        out.close();
        
        
        DSAPublicKey  readPublicKey;
        ObjectInputStream in=new ObjectInputStream (new FileInputStream("c:/temp/public.key"));
        readPublicKey=(DSAPublicKey)in.readObject();
        System.out.println("�ļ��ж�����:"+Base64.encodeBase64String(readPublicKey.getEncoded()));
        in.close();
				
	}

}
