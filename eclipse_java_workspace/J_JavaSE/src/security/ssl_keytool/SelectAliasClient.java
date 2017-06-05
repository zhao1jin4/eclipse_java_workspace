package security.ssl_keytool;

import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;

/**
 * 该类演示了如何在KeyStore中使用指定的别名。该类获取一组KeyManager，
 * 然后将它们包装在一个可配置的KeyManager中。通过指定别名，这个可配置的
 * KeyManager让包装在其中的特定的KeyManager完成处理工作.
 */
public class SelectAliasClient extends CustomTrustStoreClient
{
  private String alias=null;

  /**
   * 该方法覆盖了SimpleSSLClient类中的main()方法,目的是为了使用
   * SelectAliasClient.
   */
  public static void main(String args[])
  {
    SelectAliasClient client=new SelectAliasClient();
    client.runClient(args);
    client.close();
  }

  /**
   * 该方法覆盖了SimpleSSLClient类中的handleCommandLineOption()方法.
   * 现在该方法可以处理-alias参数了.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // 这里仅仅处理了-alias参数,其他参数传递给父类中的方法处理.
      if (arg.equals("-ALIAS")) {
        alias=args[i+1];
        out=2;
      }
      else out=0;
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
    System.out.println("\t-alias\talias to use");
  }

  /**
   * 提供了一个SSLSocketFactory对象.该对象忽略JSSE选择的KeyStore,TrustStore
   * 和别名.对于TrustStore和KeyStore,如果在命令行中指定了,使用命令行中提供的信息;
   * 如果没有指定,使用在代码中指定的信息.对于别名,如果在命令行中指定了,使用命令行中
   * 提供的信息; 如果没有指定,使用JSSE的缺省设置.
   * 该方法调用CustomKeyStoreClient类中的getKeyManagers()和CustomTrustStoreClient
   * 中的getTrustManagers()方法加载KeyStore和TrustStore.然后将返回的KeyManagers包装在
   * AliasForcingKeyManager对象中, 该对象确保正确的别名对应的授权被选中.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, GeneralSecurityException
  {
    // 调用父类中的方法获得TrustManager和KeyManager
    KeyManager[] kms=getKeyManagers();
    TrustManager[] tms=getTrustManagers();

    // 如果指定了别名，将KeyManagers包装在AliasForcingKeyManager对象中
    if (alias!=null) {
      for (int i=0; i<kms.length; i++) {
        // 这里只处理了X509KeyManager接口
        if (kms[i] instanceof X509KeyManager)
          kms[i]=new AliasForcingKeyManager((X509KeyManager)kms[i], alias);
      }
    }

    // 利用TrustManagers和已经被包装的KeyManagers创建一个SSLContext using对象.
    SSLContext context=SSLContext.getInstance("SSL");
    context.init(kms, tms, null);

    // 获得SocketFactory对象.
    SSLSocketFactory ssf=context.getSocketFactory();
    return ssf;
  }

  /**
   * AliasForcingKeyManager是X509KeyManager接口的实现类.该类包装了一个
   * 已经存在的X509KeyManager对象并使用指定的别名.如果别名无效,则不建立连接.
   */
  private class AliasForcingKeyManager implements X509KeyManager
  {
    X509KeyManager baseKM=null;
    String alias=null;

    public AliasForcingKeyManager(X509KeyManager keyManager, String alias)
    {
      baseKM=keyManager;
      this.alias=alias;
    }

    /**
     * chooseClientAlias选择一个别名来验证客户端的SSL连接.该方法使用了
	 * getClientAliases方法来获得有效别名的列表,然后将指定的别名和列表
	 * 中的别名进行比较.如果指定的别名是有效的,该方法正常返回,否则返回null.
     */
    public String chooseClientAlias(String[] keyType, Principal[] issuers,
                                    Socket socket)
    {
      // 对于每一种类型的授权,都需要调用一次getClientAliases()方法来验
	  // 证别名是否有效.
      boolean aliasFound=false;

      for (int i=0; i<keyType.length && !aliasFound; i++) {
        String[] validAliases=baseKM.getClientAliases(keyType[i], issuers);
        if (validAliases!=null) {
          for (int j=0; j<validAliases.length && !aliasFound; j++) {
            if (validAliases[j].equals(alias)) aliasFound=true;
          }
        }
      }

      if (aliasFound) return alias;
      else return null;
    }

    // 下面的方法调用缺省KeyManager中的对应方法

    public String chooseServerAlias(String keyType, Principal[] issuers,
                                    Socket socket)
    {
      return baseKM.chooseServerAlias(keyType, issuers, socket);
    }

    public X509Certificate[] getCertificateChain(String alias)
    {
      return baseKM.getCertificateChain(alias);
    }

    public String[] getClientAliases(String keyType, Principal[] issuers)
    {
      return baseKM.getClientAliases(keyType, issuers);
    }

    public PrivateKey getPrivateKey(String alias)
    {
      return baseKM.getPrivateKey(alias);
    }

    public String[] getServerAliases(String keyType, Principal[] issuers)
    {
      return baseKM.getServerAliases(keyType, issuers);
    }
  }
}
