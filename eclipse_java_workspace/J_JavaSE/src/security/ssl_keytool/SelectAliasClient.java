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
 * ������ʾ�������KeyStore��ʹ��ָ���ı����������ȡһ��KeyManager��
 * Ȼ�����ǰ�װ��һ�������õ�KeyManager�С�ͨ��ָ����������������õ�
 * KeyManager�ð�װ�����е��ض���KeyManager��ɴ�����.
 */
public class SelectAliasClient extends CustomTrustStoreClient
{
  private String alias=null;

  /**
   * �÷���������SimpleSSLClient���е�main()����,Ŀ����Ϊ��ʹ��
   * SelectAliasClient.
   */
  public static void main(String args[])
  {
    SelectAliasClient client=new SelectAliasClient();
    client.runClient(args);
    client.close();
  }

  /**
   * �÷���������SimpleSSLClient���е�handleCommandLineOption()����.
   * ���ڸ÷������Դ���-alias������.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // �������������-alias����,�����������ݸ������еķ�������.
      if (arg.equals("-ALIAS")) {
        alias=args[i+1];
        out=2;
      }
      else out=0;
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
    System.out.println("\t-alias\talias to use");
  }

  /**
   * �ṩ��һ��SSLSocketFactory����.�ö������JSSEѡ���KeyStore,TrustStore
   * �ͱ���.����TrustStore��KeyStore,�������������ָ����,ʹ�����������ṩ����Ϣ;
   * ���û��ָ��,ʹ���ڴ�����ָ������Ϣ.���ڱ���,�������������ָ����,ʹ����������
   * �ṩ����Ϣ; ���û��ָ��,ʹ��JSSE��ȱʡ����.
   * �÷�������CustomKeyStoreClient���е�getKeyManagers()��CustomTrustStoreClient
   * �е�getTrustManagers()��������KeyStore��TrustStore.Ȼ�󽫷��ص�KeyManagers��װ��
   * AliasForcingKeyManager������, �ö���ȷ����ȷ�ı�����Ӧ����Ȩ��ѡ��.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, GeneralSecurityException
  {
    // ���ø����еķ������TrustManager��KeyManager
    KeyManager[] kms=getKeyManagers();
    TrustManager[] tms=getTrustManagers();

    // ���ָ���˱�������KeyManagers��װ��AliasForcingKeyManager������
    if (alias!=null) {
      for (int i=0; i<kms.length; i++) {
        // ����ֻ������X509KeyManager�ӿ�
        if (kms[i] instanceof X509KeyManager)
          kms[i]=new AliasForcingKeyManager((X509KeyManager)kms[i], alias);
      }
    }

    // ����TrustManagers���Ѿ�����װ��KeyManagers����һ��SSLContext using����.
    SSLContext context=SSLContext.getInstance("SSL");
    context.init(kms, tms, null);

    // ���SocketFactory����.
    SSLSocketFactory ssf=context.getSocketFactory();
    return ssf;
  }

  /**
   * AliasForcingKeyManager��X509KeyManager�ӿڵ�ʵ����.�����װ��һ��
   * �Ѿ����ڵ�X509KeyManager����ʹ��ָ���ı���.���������Ч,�򲻽�������.
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
     * chooseClientAliasѡ��һ����������֤�ͻ��˵�SSL����.�÷���ʹ����
	 * getClientAliases�����������Ч�������б�,Ȼ��ָ���ı������б�
	 * �еı������бȽ�.���ָ���ı�������Ч��,�÷�����������,���򷵻�null.
     */
    public String chooseClientAlias(String[] keyType, Principal[] issuers,
                                    Socket socket)
    {
      // ����ÿһ�����͵���Ȩ,����Ҫ����һ��getClientAliases()��������
	  // ֤�����Ƿ���Ч.
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

    // ����ķ�������ȱʡKeyManager�еĶ�Ӧ����

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
