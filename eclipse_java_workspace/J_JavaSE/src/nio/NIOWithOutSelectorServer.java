package nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NIOWithOutSelectorServer {

	public static void tcpServerWithoutSelector() throws Exception
	{
		//Socket,ServerSocket,DatagramSocket都有getChannel方法,传统方式创建  总是返回null,应该使用.open()创建 
//		
//			Socket socket=new Socket();
//			SocketChannel socketChannel =socket.getChannel();
//			
//			ServerSocket ser=new ServerSocket (6000);
//			ServerSocketChannel serverSocketChannel=ser.getChannel();
//			
//			DatagramSocket rev=new DatagramSocket(7000);
//			DatagramChannel c=rev.getChannel();	
			
			//------------------- 
			
			ServerSocketChannel newServerChannel =ServerSocketChannel.open();
			newServerChannel.bind(new InetSocketAddress(9999));
			//newServerChannel.socket().bind(new InetSocketAddress(9999));
			
			//newserverChannel.configureBlocking(true);//这里可以设置为true,也是默认值
			//newserverChannel.configureBlocking(false);//是为read方法用
			SocketChannel newClientChannel =newServerChannel.accept();//当configureBlocking(false)时,如无连接 会立即返回null
			
			ByteBuffer buffer=ByteBuffer.allocate(20);
			
			int readed=newClientChannel.read(buffer);
			
			System.out.println(new String(buffer.array(),0,readed,Charset.forName("GBK")));
			System.out.println(new String(buffer.array(),0,buffer.limit(),Charset.forName("GBK")));
			
			newClientChannel.write(ByteBuffer.wrap("服务端数据".getBytes(  Charset.forName("GBK")  ) ));
			
			newClientChannel.close();
			newServerChannel.close();
	}
	
	
	public static void udpServerWithoutSelector() throws Exception
	{
		ByteBuffer buffer=ByteBuffer.allocate(20);
		
		//任意次数地进行连接或断开连接
		DatagramChannel channel = DatagramChannel.open( ); 
		
		channel.bind(new InetSocketAddress (9999));
		
		channel.configureBlocking(true);
		SocketAddress remote=channel.receive(buffer);
		buffer.flip();
		System.out.println(buffer.limit());
		System.out.println(new String(buffer.array(),0,buffer.limit(),Charset.forName("GBK")));
		
		
		ByteBuffer sendBuffer=ByteBuffer.wrap("服务端测试".getBytes(Charset.forName("GBK")));
		channel.send(sendBuffer, remote);
		
//		DatagramSocket dataGramSocket = channel.socket( ); 
//		dataGramSocket.bind (new InetSocketAddress (6666));
	}
	
	public static void main(String[] args) throws Exception 
	{
		//tcpServerWithoutSelector();//OK
		udpServerWithoutSelector();//OK
	}
}
