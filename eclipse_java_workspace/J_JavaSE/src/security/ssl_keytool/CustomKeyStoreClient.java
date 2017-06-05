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
 * 该类演示了如何创建和配置KeyStore.它加载了名为"ClientKeys"的KeyStore. 
 * 也通过命令行参数可以设置加载的KeyStore.
 */
class CustomKeyStoreClient extends SimpleSSLClient
{
  private final String DEFAULT_KEYSTORE="clientKeys";
  private final String DEFAULT_KEYSTORE_PASSWORD="password";

  private String keyStore=DEFAULT_KEYSTORE;
  private String keyStorePassword=DEFAULT_KEYSTORE_PASSWORD;

  /**
   * 该方法覆盖了SimpleSSLClient类中的main()方法,目的是为了使用
   * CustomKeyStoreClient.
   */
  public static void main(String args[])
  {
    CustomKeyStoreClient client=new CustomKeyStoreClient();
    client.runClient(args);
    client.close();
  }

  /**
   * 该方法覆盖了SimpleSSLClient类中的handleCommandLineOption()方法.
   * 现在该方法可以处理-ks和-kspass参数了.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // 这里仅仅处理了-ks和-kspass参数,其他参数传递给父类中的方法处理.
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
    System.out.println("\t-ks\tkeystore (default '"
                       +DEFAULT_KEYSTORE+"', JKS format)");
    System.out.println("\t-kspass\tkeystore password (default '"
                       +DEFAULT_KEYSTORE_PASSWORD+"')");
  }

  /**
   * 提供了一个SSLSocketFactory对象.该对象忽略JSSE选择的keystore,
   * 而使用在代码中指定的文件名和密钥,如果命令行中指定了文件名和密钥,则使用
   * 在命令行中指定的信息.
   * 该方法调用getKeyManagers()方法完成大部分的核心工作.在该方法中只需要
   * 创建一个SSLContext对象,然后从该对象中获得SSLSocketFactory对象.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, GeneralSecurityException
  {
    // 调用getKeyManagers方法获得key manager
    KeyManager[] kms=getKeyManagers();

    // 利用KeyManagers创建一个SSLContext对象. 我们使用空的TrustManager和
	// SecureRandom对象作为参数初始化SSLContext对象,这样JSSE会使用默认
	// TrustManager和SecureRandom对象.
    SSLContext context=SSLContext.getInstance("SSL");
    context.init(kms, null, null);

    // 最后获得了SocketFactory对象.
    SSLSocketFactory ssf=context.getSocketFactory();
    return ssf;
  }

  /**
   * 该方法返回一组KeyManagers对象,这些对象使用指定的KeyStore.
   */
  protected KeyManager[] getKeyManagers()
    throws IOException, GeneralSecurityException
  {
    // 获得KeyManagerFactory对象.
    String alg=KeyManagerFactory.getDefaultAlgorithm();
    KeyManagerFactory kmFact=KeyManagerFactory.getInstance(alg);
    
    // 配置KeyManagerFactory对象使用的KeyStoree.我们通过一个文件加载
    // KeyStore.
    FileInputStream fis=new FileInputStream(keyStore);
    KeyStore ks=KeyStore.getInstance("jks");
    ks.load(fis, keyStorePassword.toCharArray());
    fis.close();

    // 使用获得的KeyStore初始化KeyManagerFactory对象
    kmFact.init(ks, keyStorePassword.toCharArray());

    // 获得KeyManagers对象
    KeyManager[] kms=kmFact.getKeyManagers();
    return kms;
  }
}
