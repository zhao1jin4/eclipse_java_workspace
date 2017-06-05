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
 * 该类演示了如何创建和配置TrustStore.它加载了名为"ClientKeys"的TrustStore. 
 * 也通过命令行参数可以设置加载的TrustStore.
 */
class CustomTrustStoreClient extends CustomKeyStoreClient
{
  private final String DEFAULT_TRUSTSTORE="clientTrust";
  private final String DEFAULT_TRUSTSTORE_PASSWORD="password";

  private String trustStore=DEFAULT_TRUSTSTORE;
  private String trustStorePassword=DEFAULT_TRUSTSTORE_PASSWORD;

  /**
   * 该方法覆盖了SimpleSSLClient类中的main()方法,目的是为了使用
   * CustomTrustStoreClient.
   */
  public static void main(String args[])
  {
    CustomTrustStoreClient client=new CustomTrustStoreClient();
    client.runClient(args);
    client.close();
  }

  /**
   * 该方法覆盖了SimpleSSLClient类中的handleCommandLineOption()方法.
   * 现在该方法可以处理-ts和-tspass参数了.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // 这里仅仅处理了-ts和-tspass参数,其他参数传递给父类中的方法处理.
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
      // 解析参数时发生了错误.
      out=0;
    }

    return out;
  }

  /**
   * 显示命令行的使用方法.
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
   * 提供了一个SSLSocketFactory对象.该对象忽略JSSE选择的TrustStore,
   * 而使用在代码中指定的文件名和密钥,如果命令行中指定了文件名和密钥,则使用
   * 在命令行中指定的信息.
   * 该方法调用getTrustManagers()方法完成大部分的核心工作.在该方法中只需要
   * 创建一个SSLContext对象,然后从该对象中获得SSLSocketFactory对象.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, GeneralSecurityException
  {
    // 调用getTrustManagers方法获得trust managers
    TrustManager[] tms=getTrustManagers();
    
    // 调用getKeyManagers方法获得key manager
    KeyManager[] kms=getKeyManagers();

    // 利用KeyManagers创建一个SSLContext对象.用获得的KeyStore和
    // TrustStore初始化该SSLContext对象.我们使用缺省的SecureRandom.
    SSLContext context=SSLContext.getInstance("SSL");
    context.init(kms, tms, null);

    // 最后获得了SocketFactory对象.
    SSLSocketFactory ssf=context.getSocketFactory();
    return ssf;
  }

  /**
   * 该方法返回一组TrustManagers对象,这些对象使用指定的TrustyStore.
   */
  protected TrustManager[] getTrustManagers()
    throws IOException, GeneralSecurityException
  {
    // 首先获得缺省的TrustManagerFactory对象.
    String alg=TrustManagerFactory.getDefaultAlgorithm();
    TrustManagerFactory tmFact=TrustManagerFactory.getInstance(alg);
    
    // 配置KeyManagerFactory对象使用的TrustStoree.我们通过一个文件加载
    // TrustStore.
    FileInputStream fis=new FileInputStream(trustStore);
    KeyStore ks=KeyStore.getInstance("jks");
    ks.load(fis, trustStorePassword.toCharArray());
    fis.close();

    // 使用获得的KeyStore初始化TrustManagerFactory对象
    tmFact.init(ks);

    // 获得TrustManagers对象
    TrustManager[] tms=tmFact.getTrustManagers();
    return tms;
  }
}
