package security.ssl_keytool;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * ������ʾ����δ���������TrustStore.����������Ϊ"ClientKeys"��TrustStore. 
 * Ҳͨ�������в����������ü��ص�TrustStore.
 */
class CustomTrustStoreClient extends CustomKeyStoreClient
{
  private final String DEFAULT_TRUSTSTORE="clientTrust";
  private final String DEFAULT_TRUSTSTORE_PASSWORD="password";

  private String trustStore=DEFAULT_TRUSTSTORE;
  private String trustStorePassword=DEFAULT_TRUSTSTORE_PASSWORD;

  /**
   * �÷���������SimpleSSLClient���е�main()����,Ŀ����Ϊ��ʹ��
   * CustomTrustStoreClient.
   */
  public static void main(String args[])
  {
    CustomTrustStoreClient client=new CustomTrustStoreClient();
    client.runClient(args);
    client.close();
  }

  /**
   * �÷���������SimpleSSLClient���е�handleCommandLineOption()����.
   * ���ڸ÷������Դ���-ts��-tspass������.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // �������������-ts��-tspass����,�����������ݸ������еķ�������.
      if (arg.equals("-TS")) {
        trustStore=args[i+1];
        out=2;
      }
      else if (arg.equals("-TSPASS")) {
        trustStorePassword=args[i+1];
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
    System.out.println("\t-ts\ttruststore (default '"
                       +DEFAULT_TRUSTSTORE+"', JKS format)");
    System.out.println("\t-tspass\ttruststore password (default '"
                       +DEFAULT_TRUSTSTORE_PASSWORD+"')");
  }  

  /**
   * �ṩ��һ��SSLSocketFactory����.�ö������JSSEѡ���TrustStore,
   * ��ʹ���ڴ�����ָ�����ļ�������Կ,�����������ָ�����ļ�������Կ,��ʹ��
   * ����������ָ������Ϣ.
   * �÷�������getTrustManagers()������ɴ󲿷ֵĺ��Ĺ���.�ڸ÷�����ֻ��Ҫ
   * ����һ��SSLContext����,Ȼ��Ӹö����л��SSLSocketFactory����.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, GeneralSecurityException
  {
    // ����getTrustManagers�������trust managers
    TrustManager[] tms=getTrustManagers();
    
    // ����getKeyManagers�������key manager
    KeyManager[] kms=getKeyManagers();

    // ����KeyManagers����һ��SSLContext����.�û�õ�KeyStore��
    // TrustStore��ʼ����SSLContext����.����ʹ��ȱʡ��SecureRandom.
    SSLContext context=SSLContext.getInstance("SSL");
    context.init(kms, tms, null);

    // �������SocketFactory����.
    SSLSocketFactory ssf=context.getSocketFactory();
    return ssf;
  }

  /**
   * �÷�������һ��TrustManagers����,��Щ����ʹ��ָ����TrustyStore.
   */
  protected TrustManager[] getTrustManagers()
    throws IOException, GeneralSecurityException
  {
    // ���Ȼ��ȱʡ��TrustManagerFactory����.
    String alg=TrustManagerFactory.getDefaultAlgorithm();
    TrustManagerFactory tmFact=TrustManagerFactory.getInstance(alg);
    
    // ����KeyManagerFactory����ʹ�õ�TrustStoree.����ͨ��һ���ļ�����
    // TrustStore.
    FileInputStream fis=new FileInputStream(trustStore);
    KeyStore ks=KeyStore.getInstance("jks");
    ks.load(fis, trustStorePassword.toCharArray());
    fis.close();

    // ʹ�û�õ�KeyStore��ʼ��TrustManagerFactory����
    tmFact.init(ks);

    // ���TrustManagers����
    TrustManager[] tms=tmFact.getTrustManagers();
    return tms;
  }
}
