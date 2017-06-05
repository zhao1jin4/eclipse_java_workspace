package security.ssl_keytool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * ����һ���򵥿ͻ���,����������˽���һ��SSL����.���г�����û������뽫
 * �����͵���������.ȱʡ�����ʹ��'localhost'��49152�˿�, Ҳ����ͨ����
 * ����ָ�����������Ͷ˿ں�.ʹ��Ctrl-C��ֹ�ó���.
 */
public class SimpleSSLClient
{
  private static final int DEFAULT_PORT=49152;
  private static final String DEFAULT_HOST="localhost";

  private SSLSocket socket;
  private String host=DEFAULT_HOST;
  private int port=DEFAULT_PORT;

  /**
   * main()��������һ��SimpleSSLClient����,Ȼ�����runClient()����
 * @throws Exception 
   */
  public static void main(String args[]) throws Exception
  {
	  
	  String root="D:/program/eclipse_java_workspace/J_JavaSE/src/security/JSSE_keytool";
	  /*  
	  System.setProperty("javax.net.ssl.keyStore", 			root+"/clientKeystore");
	  System.setProperty("javax.net.ssl.keyStorePassword",	"clientkeystorepass");
	  System.setProperty("javax.net.ssl.trustStore",			root+"/clientTruststore");
	  System.setProperty("javax.net.ssl.trustStorePassword",	"clienttruststorepass");

	  */
	  SSLContext sslContext= SSLContext.getInstance("TLS");//SSL
	  	SSLContext.setDefault(sslContext); 

	  	// First initialize the key and trust material.
	  	KeyStore ksKeys = KeyStore.getInstance("JKS");
	  	ksKeys.load(new FileInputStream(root+"/clientKeystore"), "password".toCharArray());//clientkeystorepass
	  	
	  	KeyStore ksTrust = KeyStore.getInstance("JKS");
	  	ksTrust.load(new FileInputStream(root+"/clientTruststore"), "password".toCharArray());//clienttruststorepass
	  	// KeyManager's decide which key material to use.
	  	KeyManagerFactory kmf =  KeyManagerFactory.getInstance("SunX509");// KeyManagerFactory.getDefaultAlgorithm();
	  	kmf.init(ksKeys, "password".toCharArray());//������ʱ����Ҫ����һ�����е�,,,zhangsankeypass,lisikeypass

	  	// TrustManager's decide whether to allow connections.
	  	TrustManagerFactory tmf =  TrustManagerFactory.getInstance("SunX509");
	  	tmf.init(ksTrust);
	  	sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
	 	
	  	
	  	
	  	
    SimpleSSLClient client=new SimpleSSLClient();
    client.runClient(args);
    client.close();
  }
  
  /**
   * �÷������в�������.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // �÷���Ŀǰֻ֧��"-port"��"-host"����
      if (arg.equals("-PORT")) {
        port=Integer.parseInt(args[i+1]);
        out=2;
      }
      else if (arg.equals("-HOST")) {
        host=args[i+1];
        out=2;
      }
      else out=0;
    }
    catch(Exception e) {
      // ������������
      out=0;
    }

    return out;
  }

  /**
   * �ṩ�ͻ���ʹ�õ�SSLSocketFactory����.���ڷ��ص����������Ĭ��
   * SSLSocketFactory����.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, java.security.GeneralSecurityException
  {
    return (SSLSocketFactory)SSLSocketFactory.getDefault();
  }

  /**
   * �����������ʾ�����ʹ�÷���.
   */
  protected void displayUsage()
  {
    System.out.println("Options:");
    System.out.println("\t-host\thost of server (default '"+DEFAULT_HOST+"')");
    System.out.println("\t-port\tport of server (default "+DEFAULT_PORT+")");
  }  


  /**
   * runClient()�������ȴ��������в���,Ȼ�󽫿ͻ����������Ϣ���͵���������.
   */
  public void runClient(String args[])
  {
    // ���������в���
    boolean parseFailed=false;
    int i=0;
    while (i<args.length && !parseFailed) {
      int handled=handleCommandLineOption(args, i);
      if (handled==0) parseFailed=true;
      else i+=handled;
    }
    
    if (parseFailed) {
      // ��������в�����ʱ���ִ���,��ʾ��ʾ��Ϣ
      displayUsage();
    }
    else {
      try {
        // �����н��ͳɹ�.ʹ��SSLSocketFactory�������ӵ�������.
        SSLSocketFactory ssf=getSSLSocketFactory();
        connect(ssf);
        System.out.println("Connected");
        
        // ���ӳɹ�,���û����뷢�͵���������.
        transmit(System.in);
      }
      catch (IOException ioe) {
        // ����ʧ��.
        System.out.println("Connection failed: "+ioe);
      }
      catch (java.security.GeneralSecurityException gse) {
        // ����ʧ��.
        System.out.println("Connection failed: "+gse);
      }        
    }
  }

  /**
   * ���ӵ�������,��SSL���ֹ��̽����󷵻�.
   */
  public void connect(SSLSocketFactory sf) throws IOException
  {
    socket=(SSLSocket)sf.createSocket(host, port);

    try {
      socket.startHandshake();
    }
    catch (IOException ioe) {
      // ����ʧ��.�ر�����.
      try {
        socket.close();
      }
      catch (IOException ioe2) {
        // ���Ըô���.
      }
      socket=null;
      throw ioe;
    }
  }

  /**
   * ���û����봫�䵽��������.�ڵ��ø÷���ǰ�������connect()����.�ڳ�������
   * ��������˳�:1.Ctrl-C; 2.������βCtrl-D; 3.�������.
   */
  public void transmit(InputStream in)
  {
    try {
      // ����InputStream�����д�������һ��BufferReader����.
      // BufferedReader������Խ�����ֽ�Ϊ��.
      BufferedReader reader=new BufferedReader(new InputStreamReader(in));

      // ����һ��Writer�������׽���д������.
      OutputStreamWriter writer=null;
      try {
        writer=new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
      }
      catch (UnsupportedEncodingException uee) {    
        System.out.println("Warning: JVM cannot support UTF-8. Using default instead");
        writer=new OutputStreamWriter(socket.getOutputStream());
      }
      
      // ���ж�������,Ȼ������д���׽���.
      boolean done=false;
      while (!done) {
        String line=reader.readLine();
        if (!"quit".equals(line)) {
          writer.write(line);
          writer.write('\n');
          writer.flush();
        }
        else done=true;
      }
    }
    catch (IOException ioe) {
      // ��¼����,Ȼ���˳�
      System.out.println("Error: "+ioe);
    }

    // �ر�����,���Դ���.
    try {
      socket.close();
    }
    catch (IOException ioe) {}
  }

  /**
   * �Ͽ�����.
   */
  public void close()
  {
    try {
      if (socket!=null) socket.close();
    }
    catch (IOException ioe) {
      // ����IO����.
    }
    socket=null;
  }
}
