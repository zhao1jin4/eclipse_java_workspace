package security.signature;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;

public class MainSignature {
		public static void main(String[] args) throws Exception 
		{
			//���ļ��е�����ת����PublicKey,PrivateKey ����֤ǩ�� �� ����ǩ��
//			for(Provider provider : Security.getProviders())
//			{
//				System.out.println(provider.getInfo());
//			}
			 
			/*
			1.������֤˽Կ
			openssl genrsa -out root-key.key 1024
			
			2.������֤�������ļ�
			openssl req -new -out root-req.csr -key root-key.key -keyform PEM    
			#�н���Country Name ,Province ,City,Organization,Organizational Unit,Common Name,Email , ��С4λchallenge password,company name
			
			3.��ǩ��֤��
			openssl x509 -req -in root-req.csr -out root-cert.cer -signkey root-key.key -CAcreateserial -days 3650
			
			4.����p12��ʽ��֤��
			openssl pkcs12 -export -clcerts -in root-cert.cer -inkey root-key.key -out root.p12
			#��������,��ȷ�ϵ�
			
			5.����root.jks�ļ�
			keytool -import -v -trustcacerts -storepass 123456 -alias root -file root-cert.cer -keystore root.jks 
			
			 ��Ҫ���Ƶ�.cer�ļ�,Ҫת��pem��der
			openssl x509 -in root-cert.cer -inform PEM -out root-cert_b.cer -outform DER 

			*/
			byte[] singByte;
			{//---˽Կ
				
			String privateKeyFilePath="C:/_work/mpmt/BIS��Կ/_my_crt/root.p12";
		    String pfxPassword="abcd1234";
			    
	        String data="����һ������";
	        
	        KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");  
	        inputKeyStore.load(new FileInputStream(privateKeyFilePath), pfxPassword.toCharArray()); 
	        Enumeration<String> enums = inputKeyStore.aliases();  
	        String keyAlias =  enums.nextElement();  
	        Key key = inputKeyStore.getKey(keyAlias, pfxPassword.toCharArray());  
			String alg=key.getAlgorithm();//RSA
			
			inputKeyStore.getCertificate(keyAlias).getPublicKey();//˽Կ��Ҳ��ʹ�ù�Կ
			
			PrivateKey privateKey=(PrivateKey)key;
			//--������
//			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(key.getEncoded());
//			KeyFactory keyf = KeyFactory.getInstance("RSA");
//			PrivateKey privateKey = keyf.generatePrivate(priPKCS8);
			
			Signature signet =  Signature.getInstance("SHA1withRSA");//SHA256withRSA
			signet.initSign(privateKey);
			signet.update(data.getBytes("UTF-8"));
			
			singByte=signet.sign();
			System.out.println(byteToHexString(singByte));
			
			String singStr="2a22600c8ca0ada6ce0407e0cf2e8b6734fa52c8b88e4491c08714873b82d2a393b6e71123f4869c70c7a7ad223ad7e9d3f2144475fc8406a2c1cbe0aacf82e5cdbeb1c14e93934f30af0f19c84159006b79aa0e6ba1f2afa33fa3f2e7741aa127a1b436822b3a94a3aaee29ea03ee3ae025538f1183e6accf59117374f9a016";
			byte[]  resByte = hexStringToBtye(singStr);
			System.out.println(ByteBuffer.wrap(singByte).compareTo(ByteBuffer.wrap(resByte)));
			//System.out.println(new String(signet.sign()));
			//System.out.println(Base64.encodeBase64(signet.sign()));
			
			}
		 
			{//---��Կ
			    String publicKeyFilePath= "C:/_work/mpmt/BIS��Կ/_my_crt/root-cert.cer";
			    String data="����һ������";
			  
			    
			    CertificateFactory certificateFactory=CertificateFactory.getInstance("X.509");
		        FileInputStream in=new FileInputStream(publicKeyFilePath);
		        java.security.cert.Certificate certificate=certificateFactory.generateCertificate(in);
		        in.close();
		        
		        System.out.println(certificate.toString());  // ��ʾ֤�� �� SHA1withRSA
		        java.security.cert.X509Certificate x509=( java.security.cert.X509Certificate) certificate;
		        
		        System.out.println("�汾�� "+x509.getVersion());
		        System.out.println("���к� "+x509.getSerialNumber().toString(16));
		        System.out.println("ȫ�� "+x509.getSubjectDN());
		        System.out.println("ǩ����ȫ��n"+x509.getIssuerDN());
		        System.out.println("��Ч����ʼ�� "+x509.getNotBefore());
		        System.out.println("��Ч�ڽ����� "+x509.getNotAfter());
		        x509.checkValidity(new Date());//�޷���ֵ,���쳣
		        System.out.println("ǩ���㷨 "+x509.getSigAlgName());
		            ;
		        
		        byte[] sig=x509.getSignature();
		        System.out.println("ǩ��n"+new BigInteger(sig).toString(16));
		 
		        PublicKey pk=x509.getPublicKey();
		        byte[ ] pkenc=pk.getEncoded();
		        System.out.println("��Կ");
		        System.out.println(byteToHexString(pkenc));
			
			
		        //PublicKey  pubKey= certificate.getPublicKey();
		        //--������
		        X509EncodedKeySpec pubKeySpec = new  X509EncodedKeySpec(pkenc);
		        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

		        Signature signatureChecker = Signature.getInstance("SHA1withRSA");//SHA1withRSA,SHA256withRSA
		        signatureChecker.initVerify(pubKey);
		        signatureChecker.update(data.getBytes("UTF-8"));
		        
		        boolean isOK= signatureChecker.verify(singByte);
		    	System.out.println( isOK);
			}
			 
			
			{//JKS
				FileInputStream  in=new FileInputStream("C:/temp/clientKeystore.jks");
				 KeyStore ks=KeyStore.getInstance("JKS");
				 ks.load(in,"clientkeystorepass".toCharArray()); //   -keypass lisikeypass     -storepass clientkeystorepass  
				
				 PrivateKey pk=(PrivateKey)ks.getKey("lisi","lisikeypass".toCharArray());
				 //java.security.cert.Certificate cert=ks.getCertificate("lisi");//aliasΪ��Ŀ�ı�
				//if( ks.containsAlias("lisi"))
				//	ks.deleteEntry("lisi"); 
				 
				 Enumeration<String> e=ks.aliases();
				 while(e.hasMoreElements())
				 {
					 java.security.cert.Certificate cert=ks.getCertificate(e.nextElement());
					 PublicKey  pubKey=  cert.getPublicKey();
					 System.out.println( pubKey.getAlgorithm());
					 System.out.println("���֤����Ϣ:\n"+cert.toString());
				 }
			}

		}
		 public static boolean verifyCert(X509Certificate userCert, X509Certificate rootCert) throws Exception
		 {
				PublicKey rootKey = rootCert.getPublicKey();
				userCert.checkValidity();
				userCert.verify(rootKey);
				if (!userCert.getIssuerDN().equals(rootCert.getSubjectDN()))
					return false;
				boolean isNotExpire =  new java.util.Date().before(userCert.getNotAfter());
				return isNotExpire;
		  }
		 
		// byte[] tool
		public static String byteToHexString(byte bytes[]) 
		{
			//char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			int len=bytes.length;
			StringBuffer buffer = new StringBuffer(2 * len);
			for (int i = 0;i < len; i++) 
			{	
				String c0 =Integer.toHexString( (bytes[i] & 0xf0) >> 4 );  //>>>���޷�������,��0
				String c1 =Integer.toHexString(  bytes[i] & 0x0f       );
				//������
				//char c0 = HEX[ (bytes[i] & 0xf0) >> 4 ];
				//char c1 = HEX[  bytes[i] &  0x0f ];
				buffer.append(c0);
				buffer.append(c1);
			}
			return buffer.toString();
		}

		 public static byte[] hexStringToBtye(String hex) throws Exception 
		 {
			  byte[] hexBuf = hex.getBytes("UTF-8");
			  int len = hexBuf.length;
			  byte[] resultBuf = new byte[len / 2];
			  for (int i = 0; i < len; i = i + 2) 
			  {
				   String two = new String(hexBuf, i, 2);//ÿ��ȡ����
				   resultBuf[i / 2] = (byte) Integer.parseInt(two, 16);
			  }
			 //int len = hex.length();//���ܻ�������   /2 �Ǵ����
			  return resultBuf;
		}

}
