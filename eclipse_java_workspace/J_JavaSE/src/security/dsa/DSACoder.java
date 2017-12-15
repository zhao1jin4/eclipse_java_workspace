package security.dsa;
import java.security.Key;  
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
import java.util.HashMap;  
import java.util.Map;  
import org.apache.commons.codec.binary.Base64;  
  
/** 
 * ��������ǩ���㷨DSA 
 * ����ǩ�� 
 * @author kongqz 
 * */  
public class DSACoder {  
    //����ǩ������Կ�㷨  
    public static final String KEY_ALGORITHM="DSA";  
      
    /** 
     * ����ǩ�� 
     * ǩ��/��֤�㷨 
     * */  
    public static final String SIGNATURE_ALGORITHM="SHA1withDSA";  
      
    /** 
     * DSA��Կ���ȣ�RSA�㷨��Ĭ����Կ������1024 
     * ��Կ���ȱ�����64�ı�������512��1024λ֮�� 
     * */  
    private static final int KEY_SIZE=1024;  
    //��Կ  
    private static final String PUBLIC_KEY="DSAPublicKey";  
      
    //˽Կ  
    private static final String PRIVATE_KEY="DSAPrivateKey";  
      
    /** 
     * ��ʼ����Կ�� 
     * @return Map �׷���Կ��Map 
     * */  
    public static Map<String,Object> initKey() throws Exception{  
        //ʵ������Կ������  
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ������  
        keyPairGenerator.initialize(KEY_SIZE,new SecureRandom());  
        //������Կ��  
        KeyPair keyPair=keyPairGenerator.generateKeyPair();  
        //�׷���Կ  
        DSAPublicKey publicKey=(DSAPublicKey) keyPair.getPublic();  
        //�׷�˽Կ  
        DSAPrivateKey privateKey=(DSAPrivateKey) keyPair.getPrivate();  
        //����Կ�洢��map��  
        Map<String,Object> keyMap=new HashMap<String,Object>();  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
          
    }  
      
      
    /** 
     * ǩ�� 
     * @param data��ǩ������ 
     * @param privateKey ��Կ 
     * @return byte[] ����ǩ�� 
     * */  
    public static byte[] sign(byte[] data,byte[] privateKey) throws Exception{  
          
        //ȡ��˽Կ  
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //����˽Կ  
        PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);  
        //ʵ����Signature  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        //��ʼ��Signature  
        signature.initSign(priKey);  
        //����  
        signature.update(data);  
        return signature.sign();  
    }  
    /** 
     * У������ǩ�� 
     * @param data ��У������ 
     * @param publicKey ��Կ 
     * @param sign ����ǩ�� 
     * @return boolean У��ɹ�����true��ʧ�ܷ���false 
     * */  
    public static boolean verify(byte[] data,byte[] publicKey,byte[] sign) throws Exception{  
        //ת����Կ����  
        //ʵ������Կ����  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ  
        //��Կ����ת��  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKey);  
        //������Կ  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
        //ʵ����Signature  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        //��ʼ��Signature  
        signature.initVerify(pubKey);  
        //����  
        signature.update(data);  
        //��֤  
        return signature.verify(sign);  
    }  
    /** 
     * ȡ��˽Կ 
     * @param keyMap ��Կmap 
     * @return byte[] ˽Կ 
     * */  
    public static byte[] getPrivateKey(Map<String,Object> keyMap){  
        Key key=(Key)keyMap.get(PRIVATE_KEY);  
        return key.getEncoded();  
    }  
    /** 
     * ȡ�ù�Կ 
     * @param keyMap ��Կmap 
     * @return byte[] ��Կ 
     * */  
    public static byte[] getPublicKey(Map<String,Object> keyMap) throws Exception{  
        Key key=(Key) keyMap.get(PUBLIC_KEY);  
        return key.getEncoded();  
    }  
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        //��ʼ����Կ  
        //������Կ��  
        Map<String,Object> keyMap=DSACoder.initKey();  
        //��Կ  
        byte[] publicKey=DSACoder.getPublicKey(keyMap);  
          
        //˽Կ  
        byte[] privateKey=DSACoder.getPrivateKey(keyMap);  
        System.out.println("��Կ��\n"+Base64.encodeBase64String(publicKey));  
        System.out.println("˽Կ��\n"+Base64.encodeBase64String(privateKey));  
          
        
        
        System.out.println("================��Կ�Թ������,�׷�����Կ�������ҷ�����ʼ���м������ݵĴ���=============");  
        String str="DSA����ǩ���㷨";  
        System.out.println("ԭ��:"+str);  
        //�׷��������ݵļ���  
        byte[] sign=DSACoder.sign(str.getBytes(), privateKey);  
        System.out.println("����ǩ����"+Base64.encodeBase64String(sign));  
        //��֤ǩ��  
        boolean status=DSACoder.verify(str.getBytes(), publicKey, sign);  
        System.out.println("״̬��"+status+"\n\n");  
          
          
    }  
}  