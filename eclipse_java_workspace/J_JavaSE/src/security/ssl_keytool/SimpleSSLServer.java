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
 * 该类是一个简单的服务器程序.它的功能是接受SSL连接请求,然后再标准显示设备上
 * 显示通过SSL套接字接收到的数据流.该服务器缺省运行在49152端口上,它需要进行
 * 客户端验证.通过输入Ctrl-C可以终止该程序.
 */
public class SimpleSSLServer extends Thread
{
  private static final int DEFAULT_PORT=49152;

  private SSLServerSocketFactory serverSocketFactory;
  private int port;

  /**
   * 该方法首先处理命令行参数,然后开始监听连接请求.
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
//	  System.setProperty("javax.net.ssl.keyStorePassword",	"serverkeystorepass");//是自己设的密码
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
  	SSLEngine engine = sslContext.createSSLEngine("127.0.0.1",4567);//SSLContext得到SSLEngine
  	engine.setUseClientMode(true);
  	SSLSession session=engine.getSession();//SSLEngine 得到 SSLSession
  	
   SSLSocketFactory socketFactory=	sslContext.getSocketFactory();//SSLContext得到SSLSocketFactory
   SSLSocket soket=(SSLSocket)socketFactory.createSocket();
   SSLSession session1=soket.getSession();//SSLSocket 得到 SSLSession
   
	engine.setUseClientMode(false);
  	SSLServerSocketFactory serverSocketFactory= sslContext.getServerSocketFactory();//SSLContext得到SSLServerSocketFactory
    SSLServerSocket serverSocket= (SSLServerSocket)serverSocketFactory.createServerSocket();

  SSLServerSocketFactory serverFactory2= (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
//先读ssl.ServerSocketFactory.provider属性,没用则会自动调用SSLContext.getDefault().getServerSocketFactory(). 
 
	ByteBuffer myAppData = ByteBuffer.allocate(session.getApplicationBufferSize());
	ByteBuffer myNetData = ByteBuffer.allocate(session.getPacketBufferSize());
	engine.beginHandshake();
	SSLEngineResult.HandshakeStatus hs = engine.getHandshakeStatus();
	if(hs == SSLEngineResult.HandshakeStatus.NEED_UNWRAP)//NEED_WRAP,NEED_TASK
	{
		SSLEngineResult r = engine.unwrap(myNetData, myAppData);
		if(r.getStatus() == SSLEngineResult.Status.OK ) //BUFFER_UNDERFLOW(source) ,BUFFER_OVERFLOW(destination不够大)
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
  		TrustManagerFactory factory = TrustManagerFactory.getInstance("PKIX");//PKIX 算法
  		factory.init(trustParams);

  		// Use factory
  		SSLContext ctx = SSLContext.getInstance("TLS");
  		ctx.init(null, factory.getTrustManagers(), null);
  	*/
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
	
    int port=DEFAULT_PORT;

    // 解释参数
    boolean parseFailed=false;
    try {
      for (int i=0; i<args.length; i++) {
        String arg=args[i].trim().toUpperCase();

        // 目前只支持"-port"参数
        if (arg.equals("-PORT")) port=Integer.parseInt(args[++i]);
        else parseFailed=true;
      }
    }
    catch(Exception e) {
      // 命令行参数解释错误,显示提示信息.
      parseFailed=true;
    }

    if (parseFailed) {
      displayUsage();
    }
    else {
      // 命令行参数解释成功.
      // 利用缺省的SSLServerSocketFactory对象创建一个SimpleSSLServer对象,
	  // 然后启动该SimpleSSLServer对象.
      SSLServerSocketFactory ssf= (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
      SimpleSSLServer server=new SimpleSSLServer(ssf, port);
      server.start();
    }
  }

  /** 显示命令行用法 */
  private static void displayUsage()
  {
    System.out.println("Options:");
    System.out.println("\t-port\tport of server (default "+DEFAULT_PORT+")");
  }


  /**
   *在指定端口上创建一个SimpleSSLServer对象.
   */
  public SimpleSSLServer(SSLServerSocketFactory ssf, int port)
  {
    serverSocketFactory=ssf;
    this.port=port;
  }

  /**
   * SimpleSSLServer对象运行在一个单独的线程中.run()方法中实现了一个死循环,
   * 服务器在该循环中监听客户端的连接申请.用户按下Ctrl-C可以退出该循环.
   */
  public void run()
  {
    System.out.println("SimpleSSLServer running on port "+port);

    try {
      // 首先创建一个安全套接字对象
      SSLServerSocket serverSocket=
        (SSLServerSocket)serverSocketFactory.createServerSocket(port);

      serverSocket.setNeedClientAuth(true);

      // 每个连接都有一个ID号,该ID号从1开始
      int id=1;

      // 监听连接请求.对于每个请求打开一个新的线程.
      while(true) {
        String ident=String.valueOf(id++);

        // 监听连接请求.
        SSLSocket socket=(SSLSocket)serverSocket.accept();

        // 通过使用HandshakeCompletedListener对象,程序可以进行授权验证.
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
   * HandshakeCompletedListener类显示客户端传来的授权信息.
   */
  class SimpleHandshakeListener implements HandshakeCompletedListener
  {
    String ident;

    /**
     * 构造函数.
     */
    public SimpleHandshakeListener(String ident)
    {
      this.ident=ident;
    }

    /** 当SSL握手过程完成后该方法被激活. */
    public void handshakeCompleted(HandshakeCompletedEvent event)
    {
      // 显示授权信息.
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
   * 该类将套接字接收到的数据流传送到标准输出.
   */
  class InputDisplayer extends Thread {
    BufferedReader reader;
    String ident;

    /**
     * 构造函数.
     */
    InputDisplayer(String ident, InputStream is)
    {
      this.ident=ident;
      log("New connection request");

      // 利用InputStream对象创建一个InputStreamReader对象.该InputStreamReader
      // 对象将数据流中的字节转化为字符,然后利用一个BufferedReader对象将数据流
	  // 按行分解.
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
     * 将数据按行输出到屏幕.
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
        // IO错误,记录错误并退出.
        log(ioe.toString());
        log("Closing connection.");
      }

      try {
        reader.close();
      }
      catch(IOException ioe) {}
    }

    /**
     * 该方法进行日志处理.
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
