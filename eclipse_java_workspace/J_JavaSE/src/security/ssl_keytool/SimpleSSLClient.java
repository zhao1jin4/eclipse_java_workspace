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
 * 这是一个简单客户端,它向服务器端建立一个SSL连接.运行程序后用户的输入将
 * 被传送到服务器端.缺省情况下使用'localhost'和49152端口, 也可以通过命
 * 令行指定服务器名和端口号.使用Ctrl-C终止该程序.
 */
public class SimpleSSLClient
{
  private static final int DEFAULT_PORT=49152;
  private static final String DEFAULT_HOST="localhost";

  private SSLSocket socket;
  private String host=DEFAULT_HOST;
  private int port=DEFAULT_PORT;

  /**
   * main()方法创建一个SimpleSSLClient对象,然后调用runClient()方法
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
	  	kmf.init(ksKeys, "password".toCharArray());//这里暂时密码要几个一样才行的,,,zhangsankeypass,lisikeypass

	  	// TrustManager's decide whether to allow connections.
	  	TrustManagerFactory tmf =  TrustManagerFactory.getInstance("SunX509");
	  	tmf.init(ksTrust);
	  	sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
	 	
	  	
	  	
	  	
    SimpleSSLClient client=new SimpleSSLClient();
    client.runClient(args);
    client.close();
  }
  
  /**
   * 该方法进行参数处理.
   */
  protected int handleCommandLineOption(String[] args, int i)
  {
    int out;
    try {
      String arg=args[i].trim().toUpperCase();

      // 该方法目前只支持"-port"和"-host"参数
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
      // 参数解析错误
      out=0;
    }

    return out;
  }

  /**
   * 提供客户端使用的SSLSocketFactory对象.现在返回的是虚拟机的默认
   * SSLSocketFactory对象.
   */
  protected SSLSocketFactory getSSLSocketFactory()
    throws IOException, java.security.GeneralSecurityException
  {
    return (SSLSocketFactory)SSLSocketFactory.getDefault();
  }

  /**
   * 在命令窗口中显示程序的使用方法.
   */
  protected void displayUsage()
  {
    System.out.println("Options:");
    System.out.println("\t-host\thost of server (default '"+DEFAULT_HOST+"')");
    System.out.println("\t-port\tport of server (default "+DEFAULT_PORT+")");
  }  


  /**
   * runClient()方法首先处理命令行参数,然后将客户端输入的信息传送到服务器端.
   */
  public void runClient(String args[])
  {
    // 解释命令行参数
    boolean parseFailed=false;
    int i=0;
    while (i<args.length && !parseFailed) {
      int handled=handleCommandLineOption(args, i);
      if (handled==0) parseFailed=true;
      else i+=handled;
    }
    
    if (parseFailed) {
      // 如果命令行参数解时出现错误,显示提示信息
      displayUsage();
    }
    else {
      try {
        // 命令行解释成功.使用SSLSocketFactory对象连接到服务器.
        SSLSocketFactory ssf=getSSLSocketFactory();
        connect(ssf);
        System.out.println("Connected");
        
        // 连接成功,将用户输入发送到服务器端.
        transmit(System.in);
      }
      catch (IOException ioe) {
        // 连接失败.
        System.out.println("Connection failed: "+ioe);
      }
      catch (java.security.GeneralSecurityException gse) {
        // 连接失败.
        System.out.println("Connection failed: "+gse);
      }        
    }
  }

  /**
   * 连接到服务器,当SSL握手过程结束后返回.
   */
  public void connect(SSLSocketFactory sf) throws IOException
  {
    socket=(SSLSocket)sf.createSocket(host, port);

    try {
      socket.startHandshake();
    }
    catch (IOException ioe) {
      // 握手失败.关闭连接.
      try {
        socket.close();
      }
      catch (IOException ioe2) {
        // 忽略该错误.
      }
      socket=null;
      throw ioe;
    }
  }

  /**
   * 将用户输入传输到服务器端.在调用该方法前必须调用connect()方法.在出现下面
   * 的情况是退出:1.Ctrl-C; 2.数据流尾Ctrl-D; 3.网络错误.
   */
  public void transmit(InputStream in)
  {
    try {
      // 利用InputStream对象中创建创建一个BufferReader对象.
      // BufferedReader对象可以将输入分解为行.
      BufferedReader reader=new BufferedReader(new InputStreamReader(in));

      // 创建一个Writer对象向套接字写入数据.
      OutputStreamWriter writer=null;
      try {
        writer=new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
      }
      catch (UnsupportedEncodingException uee) {    
        System.out.println("Warning: JVM cannot support UTF-8. Using default instead");
        writer=new OutputStreamWriter(socket.getOutputStream());
      }
      
      // 按行读入数据,然后将数据写入套接字.
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
      // 登录错误,然后退出
      System.out.println("Error: "+ioe);
    }

    // 关闭连接,忽略错误.
    try {
      socket.close();
    }
    catch (IOException ioe) {}
  }

  /**
   * 断开连接.
   */
  public void close()
  {
    try {
      if (socket!=null) socket.close();
    }
    catch (IOException ioe) {
      // 忽略IO错误.
    }
    socket=null;
  }
}
