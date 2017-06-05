package security.ssl_keytool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.security.cert.CertPathParameters;
import java.security.cert.CertStore;
import java.security.cert.LDAPCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;

import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


/**
 * ������һ���򵥵ķ���������.���Ĺ����ǽ���SSL��������,Ȼ���ٱ�׼��ʾ�豸��
 * ��ʾͨ��SSL�׽��ֽ��յ���������.�÷�����ȱʡ������49152�˿���,����Ҫ����
 * �ͻ�����֤.ͨ������Ctrl-C������ֹ�ó���.
 */
public class SimpleSSLServer extends Thread
{
  private static final int DEFAULT_PORT=49152;

  private SSLServerSocketFactory serverSocketFactory;
  private int port;

  /**
   * �÷������ȴ��������в���,Ȼ��ʼ������������.
 * @throws Exception 
   */
  public static void main(String args[]) throws Exception
  {
	  String root="D:/program/eclipse_java_workspace/J_JavaSE/src/security/ssl_keytool";
	  /*
	   //-keyalg RSA 
keytool -genkey -alias server -keystore C:/temp/serverKeystore  -dname "CN=lizhaojin,OU=tcs,O=tata,L=Harbin,ST=HeiLongJian,C=CN"  -keypass serverkeypass  --storepass serverkeystorepass
keytool -genkey -alias lisi  -keystore C:/temp/clientKeystore  -dname "CN=lisi,OU=tcs,O=tata,L=Harbin,ST=HeiLongJian,C=CN"  -keypass lisikeypass  --storepass clientkeystorepass
keytool -genkey -alias zhangsan  -keystore C:/temp/clientKeystore  -dname "CN=zhangsan,OU=tcs,O=tata,L=Harbin,ST=HeiLongJian,C=CN"  -keypass zhangsankeypass  --storepass clientkeystorepass

keytool -export -alias server -keystore C:/temp/serverKeystore -file server.cer -storepass serverkeystorepass 
keytool -export -alias lisi   -keystore C:/temp/clientKeystore -file lisi.cer -storepass clientkeystorepass
keytool -export -alias zhangsan -keystore C:/temp/clientKeystore -file zhangsan.cer -storepass clientkeystorepass

keytool -import -alias server -keystore C:/temp/clientTruststore -file server.cer -storepass clienttruststorepass -noprompt 
keytool -import -alias lisi -keystore C:/temp/serverTruststore -file lisi.cer -storepass servertruststorepass -noprompt 
keytool -import -alias zhangsan -keystore C:/temp/serverTruststore -file zhangsan.cer  -storepass servertruststorepass -noprompt 

	  */
	  //System.out.println(System.getProperty("ssl.ServerSocketFactory.provider"));
	  
//	  System.setProperty("javax.net.ssl.keyStore", 			root+"/serverKeystore");
//	  System.setProperty("javax.net.ssl.keyStorePassword",	"serverkeystorepass");//���Լ��������
//	  System.setProperty("javax.net.ssl.trustStore",			root+"/serverTruststore");
//	  System.setProperty("javax.net.ssl.trustStorePassword",	"servertruststorepass");
	  
	
   
	SSLContext sslContext= SSLContext.getInstance("TLS");//SSL
  	SSLContext.setDefault(sslContext); 

  	// First initialize the key and trust material.
  	KeyStore ksKeys = KeyStore.getInstance("JKS");
  	ksKeys.load(new FileInputStream(root+"/serverKeystore"), "password".toCharArray());//serverkeystorepass
  	
  	KeyStore ksTrust = KeyStore.getInstance("JKS");
  	ksTrust.load(new FileInputStream(root+"/serverTruststore"), "password".toCharArray());//servertruststorepass
  	// KeyManager's decide which key material to use.
  	KeyManagerFactory kmf =  KeyManagerFactory.getInstance("SunX509");// KeyManagerFactory.getDefaultAlgorithm();
  	kmf.init(ksKeys, "password".toCharArray());//serverkeypass

  	// TrustManager's decide whether to allow connections.
  	TrustManagerFactory tmf =  TrustManagerFactory.getInstance("SunX509");
  	tmf.init(ksTrust);
  	sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
  	
  	/*  
  	SSLEngine engine = sslContext.createSSLEngine("127.0.0.1",4567);//SSLContext�õ�SSLEngine
  	engine.setUseClientMode(true);
  	SSLSession session=engine.getSession();//SSLEngine �õ� SSLSession
  	
   SSLSocketFactory socketFactory=	sslContext.getSocketFactory();//SSLContext�õ�SSLSocketFactory
   SSLSocket soket=(SSLSocket)socketFactory.createSocket();
   SSLSession session1=soket.getSession();//SSLSocket �õ� SSLSession
   
	engine.setUseClientMode(false);
  	SSLServerSocketFactory serverSocketFactory= sslContext.getServerSocketFactory();//SSLContext�õ�SSLServerSocketFactory
    SSLServerSocket serverSocket= (SSLServerSocket)serverSocketFactory.createServerSocket();

  SSLServerSocketFactory serverFactory2= (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
//�ȶ�ssl.ServerSocketFactory.provider����,û������Զ�����SSLContext.getDefault().getServerSocketFactory(). 
 
	ByteBuffer myAppData = ByteBuffer.allocate(session.getApplicationBufferSize());
	ByteBuffer myNetData = ByteBuffer.allocate(session.getPacketBufferSize());
	engine.beginHandshake();
	SSLEngineResult.HandshakeStatus hs = engine.getHandshakeStatus();
	if(hs == SSLEngineResult.HandshakeStatus.NEED_UNWRAP)//NEED_WRAP,NEED_TASK
	{
		SSLEngineResult r = engine.unwrap(myNetData, myAppData);
		if(r.getStatus() == SSLEngineResult.Status.OK ) //BUFFER_UNDERFLOW(source) ,BUFFER_OVERFLOW(destination������)
		{}
	}else if (hs == SSLEngineResult.HandshakeStatus.NEED_TASK) 
	{
	    Runnable task=engine.getDelegatedTask();
	    new Thread(task).start();
	}

  	
  	KeyStore anchors = KeyStore.getInstance("JKS");
  	anchors.load(new FileInputStream("anchorsFile"),"password".toCharArray());
  	CertPathParameters pkixParams = new PKIXBuilderParameters(anchors,  new X509CertSelector());

  		// Specify LDAP certificate store to use
  		LDAPCertStoreParameters lcsp = new LDAPCertStoreParameters("ldap.imc.org", 389);

  		// Wrap them as trust manager parameters
  		ManagerFactoryParameters trustParams =  new CertPathTrustManagerParameters(pkixParams);

  		// Create TrustManagerFactory for PKIX-compliant trust managers
  		TrustManagerFactory factory = TrustManagerFactory.getInstance("PKIX");//PKIX �㷨
  		factory.init(trustParams);

  		// Use factory
  		SSLContext ctx = SSLContext.getInstance("TLS");
  		ctx.init(null, factory.getTrustManagers(), null);
  	*/
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
	
    int port=DEFAULT_PORT;

    // ���Ͳ���
    boolean parseFailed=false;
    try {
      for (int i=0; i<args.length; i++) {
        String arg=args[i].trim().toUpperCase();

        // Ŀǰֻ֧��"-port"����
        if (arg.equals("-PORT")) port=Integer.parseInt(args[++i]);
        else parseFailed=true;
      }
    }
    catch(Exception e) {
      // �����в������ʹ���,��ʾ��ʾ��Ϣ.
      parseFailed=true;
    }

    if (parseFailed) {
      displayUsage();
    }
    else {
      // �����в������ͳɹ�.
      // ����ȱʡ��SSLServerSocketFactory���󴴽�һ��SimpleSSLServer����,
	  // Ȼ��������SimpleSSLServer����.
      SSLServerSocketFactory ssf= (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
      SimpleSSLServer server=new SimpleSSLServer(ssf, port);
      server.start();
    }
  }

  /** ��ʾ�������÷� */
  private static void displayUsage()
  {
    System.out.println("Options:");
    System.out.println("\t-port\tport of server (default "+DEFAULT_PORT+")");
  }


  /**
   *��ָ���˿��ϴ���һ��SimpleSSLServer����.
   */
  public SimpleSSLServer(SSLServerSocketFactory ssf, int port)
  {
    serverSocketFactory=ssf;
    this.port=port;
  }

  /**
   * SimpleSSLServer����������һ���������߳���.run()������ʵ����һ����ѭ��,
   * �������ڸ�ѭ���м����ͻ��˵���������.�û�����Ctrl-C�����˳���ѭ��.
   */
  public void run()
  {
    System.out.println("SimpleSSLServer running on port "+port);

    try {
      // ���ȴ���һ����ȫ�׽��ֶ���
      SSLServerSocket serverSocket=
        (SSLServerSocket)serverSocketFactory.createServerSocket(port);

      serverSocket.setNeedClientAuth(true);

      // ÿ�����Ӷ���һ��ID��,��ID�Ŵ�1��ʼ
      int id=1;

      // ������������.����ÿ�������һ���µ��߳�.
      while(true) {
        String ident=String.valueOf(id++);

        // ������������.
        SSLSocket socket=(SSLSocket)serverSocket.accept();

        // ͨ��ʹ��HandshakeCompletedListener����,������Խ�����Ȩ��֤.
        HandshakeCompletedListener hcl=new SimpleHandshakeListener(ident);
        socket.addHandshakeCompletedListener(hcl);

        InputStream in=socket.getInputStream();
        new InputDisplayer(ident, in);
      }
    }
    catch(IOException ioe) {
      System.out.println("SimpleSSLServer failed with following exception:");
      System.out.println(ioe);
      ioe.printStackTrace();
    }
  }

  /**
   * HandshakeCompletedListener����ʾ�ͻ��˴�������Ȩ��Ϣ.
   */
  class SimpleHandshakeListener implements HandshakeCompletedListener
  {
    String ident;

    /**
     * ���캯��.
     */
    public SimpleHandshakeListener(String ident)
    {
      this.ident=ident;
    }

    /** ��SSL���ֹ�����ɺ�÷���������. */
    public void handshakeCompleted(HandshakeCompletedEvent event)
    {
      // ��ʾ��Ȩ��Ϣ.
      try {
        X509Certificate cert=(X509Certificate)event.getPeerCertificates()[0];
        String peer=cert.getSubjectDN().getName();
        System.out.println(ident+": Request from "+peer);
      }
      catch (SSLPeerUnverifiedException pue) {
        System.out.println(ident+": Peer unverified");
      }
    }
  }
    

  /**
   * ���ཫ�׽��ֽ��յ������������͵���׼���.
   */
  class InputDisplayer extends Thread {
    BufferedReader reader;
    String ident;

    /**
     * ���캯��.
     */
    InputDisplayer(String ident, InputStream is)
    {
      this.ident=ident;
      log("New connection request");

      // ����InputStream���󴴽�һ��InputStreamReader����.��InputStreamReader
      // �����������е��ֽ�ת��Ϊ�ַ�,Ȼ������һ��BufferedReader����������
	  // ���зֽ�.
      try {
        reader=new BufferedReader(new InputStreamReader(is, "UTF-8"));
      }
      catch (UnsupportedEncodingException uee) {
        log("Warning: JVM cannot support UTF-8. Using default instead");
        reader=new BufferedReader(new InputStreamReader(is));
      }

      setDaemon(true);
      start();
    }

    /**
     * �����ݰ����������Ļ.
     */
    public void run()
    {
      boolean done=false;

      try {
        while (!done) {
          String line=reader.readLine();
          if (line!=null) display(line);
          else done=true;
        }
        log("Client disconnected");
      }
      catch(IOException ioe) {
        // IO����,��¼�����˳�.
        log(ioe.toString());
        log("Closing connection.");
      }

      try {
        reader.close();
      }
      catch(IOException ioe) {}
    }

    /**
     * �÷���������־����.
     */
    private void log(String text)
    {
      System.out.println(ident+": "+text);
    }

    private void display(String text)
    {
      System.out.println(ident+"> "+text);
    }
  }
}
