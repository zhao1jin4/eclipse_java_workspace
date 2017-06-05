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
		//Socket,ServerSocket,DatagramSocket����getChannel����,��ͳ��ʽ����  ���Ƿ���null,Ӧ��ʹ��.open()���� 
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
			
			//newserverChannel.configureBlocking(true);//�����������Ϊtrue,Ҳ��Ĭ��ֵ
			//newserverChannel.configureBlocking(false);//��Ϊread������
			SocketChannel newClientChannel =newServerChannel.accept();//��configureBlocking(false)ʱ,�������� ����������null
			
			ByteBuffer buffer=ByteBuffer.allocate(20);
			
			int readed=newClientChannel.read(buffer);
			
			System.out.println(new String(buffer.array(),0,readed,Charset.forName("GBK")));
			System.out.println(new String(buffer.array(),0,buffer.limit(),Charset.forName("GBK")));
			
			newClientChannel.write(ByteBuffer.wrap("���������".getBytes(  Charset.forName("GBK")  ) ));
			
			newClientChannel.close();
			newServerChannel.close();
	}
	
	
	public static void udpServerWithoutSelector() throws Exception
	{
		ByteBuffer buffer=ByteBuffer.allocate(20);
		
		//��������ؽ������ӻ�Ͽ�����
		DatagramChannel channel = DatagramChannel.open( ); 
		
		channel.bind(new InetSocketAddress (9999));
		
		channel.configureBlocking(true);
		SocketAddress remote=channel.receive(buffer);
		buffer.flip();
		System.out.println(buffer.limit());
		System.out.println(new String(buffer.array(),0,buffer.limit(),Charset.forName("GBK")));
		
		
		ByteBuffer sendBuffer=ByteBuffer.wrap("����˲���".getBytes(Charset.forName("GBK")));
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
