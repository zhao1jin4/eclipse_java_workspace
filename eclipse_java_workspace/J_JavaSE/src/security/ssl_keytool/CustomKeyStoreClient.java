package security.ssl_keytool;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * ������ʾ����δ���������KeyStore.����������Ϊ"ClientKeys"��KeyStore. 
 * Ҳͨ�������в����������ü��ص�KeyStore.
 */
class CustomKeyStoreClient extends SimpleSSLClient
{
  private final String DEFAULT_KEYSTORE="clientKeys";
  private final String DEFAULT_KEYSTORE_PASSWORD="password";

  private String keyStore=DEFAULT_KEYSTORE;
  private String keyStorePassword=DEFAULT_KEYSTORE_PASSWORD;

  /**
   * �÷���������SimpleSSLClient���е�main()����,Ŀ����Ϊ��ʹ��
   * CustomKeyStoreClient.
   */
  public static void main(String args[])
  {
    CustomKeyStoreClient client=new CustomKeyStoreClient();
    client.runClient(args);
    client.close();
  }

  /**
   * �÷���������SimpleSSLClient���е�handleCommandLineOption()����.
   * ���ڸ÷������Դ���-ks��-kspass������.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // �������������-ks��-kspass����,�����������ݸ������еķ�������.
      if (arg.equals("-KS")) {
        keyStore=args[i+1];
        out=2;
      }
      else if (arg.equals("-KSPASS")) {
        keyStorePassword=args[i+1];
        out=2;
      }
      else out=super.handleCommandLineOption(args,i);
    }
    catch(Exception e) {
      // ��������ʱ�����˴���.
      out=0;
    }

    return out;
  }

  /**
   * ��ʾ�����е�ʹ�÷���.
   */
  protected void displayUsage()
  {
    super.displayUsage();
    System.out.println("\t-ks\tkeystore (default '"
                       +DEFAULT_KEYSTORE+"', JKS format)");
    System.out.println("\t-kspass\tkeystore password (default '"
                       +DEFAULT_KEYSTORE_PASSWORD+"')");
  }

  /**
   * �ṩ��һ��SSLSocketFactory����.�ö������JSSEѡ���keystore,
   * ��ʹ���ڴ�����ָ�����ļ�������Կ,�����������ָ�����ļ�������Կ,��ʹ��
   * ����������ָ������Ϣ.
   * �÷�������getKeyManagers()������ɴ󲿷ֵĺ��Ĺ���.�ڸ÷�����ֻ��Ҫ
   * ����һ��SSLContext����,Ȼ��Ӹö����л��SSLSocketFactory����.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, GeneralSecurityException
  {
    // ����getKeyManagers�������key manager
    KeyManager[] kms=getKeyManagers();

    // ����KeyManagers����һ��SSLContext����. ����ʹ�ÿյ�TrustManager��
	// SecureRandom������Ϊ������ʼ��SSLContext����,����JSSE��ʹ��Ĭ��
	// TrustManager��SecureRandom����.
    SSLContext context=SSLContext.getInstance("SSL");
    context.init(kms, null, null);

    // �������SocketFactory����.
    SSLSocketFactory ssf=context.getSocketFactory();
    return ssf;
  }

  /**
   * �÷�������һ��KeyManagers����,��Щ����ʹ��ָ����KeyStore.
   */
  protected KeyManager[] getKeyManagers()
    throws IOException, GeneralSecurityException
  {
    // ���KeyManagerFactory����.
    String alg=KeyManagerFactory.getDefaultAlgorithm();
    KeyManagerFactory kmFact=KeyManagerFactory.getInstance(alg);
    
    // ����KeyManagerFactory����ʹ�õ�KeyStoree.����ͨ��һ���ļ�����
    // KeyStore.
    FileInputStream fis=new FileInputStream(keyStore);
    KeyStore ks=KeyStore.getInstance("jks");
    ks.load(fis, keyStorePassword.toCharArray());
    fis.close();

    // ʹ�û�õ�KeyStore��ʼ��KeyManagerFactory����
    kmFact.init(ks, keyStorePassword.toCharArray());

    // ���KeyManagers����
    KeyManager[] kms=kmFact.getKeyManagers();
    return kms;
  }
}
