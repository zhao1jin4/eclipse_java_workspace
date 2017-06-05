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

public class NIOWithOutSelectorClient {
	
	public static void tcpClientWithoutSelector() throws Exception {
		
		SocketChannel clientSocketChannel=SocketChannel.open();
		//clientSocketChannel.configureBlocking(false);
		//clientSocketChannel.configureBlocking(true);//默认true
		
		if(!clientSocketChannel.isConnected())
		{
			boolean isOK=clientSocketChannel.connect(new InetSocketAddress(9999));
			//如 configureBlocking(false) 未连接会立即返回false,而不会报错
			
			if(clientSocketChannel.isConnectionPending())
			{
				boolean finishConnect=clientSocketChannel.finishConnect();
			}
			boolean isConnected=clientSocketChannel.isConnected();
		}
		int writed=clientSocketChannel.write(ByteBuffer.wrap("客户端的数据".getBytes(Charset.forName("GBK"))));
		
		ByteBuffer buffer=ByteBuffer.allocate(20);
		int readed=clientSocketChannel.read(buffer);
		System.out.println(new String(buffer.array(),0,readed,Charset.forName("GBK")));
		System.out.println(new String(buffer.array(),0,buffer.limit(),Charset.forName("GBK")));
		
		clientSocketChannel.close();
		
	}
	public static void udpClientWithoutSelector() throws Exception {
		
		DatagramChannel channel = DatagramChannel.open( ); 
		channel.configureBlocking(true);
		
		ByteBuffer buffer=ByteBuffer.wrap("123客户端测试".getBytes(Charset.forName("GBK")));
		
		int n=channel.send(buffer, new InetSocketAddress("127.0.0.1", 9999 ));
		
		ByteBuffer revBuffer=ByteBuffer.allocate(20);
		SocketAddress remote=channel.receive(revBuffer);
		revBuffer.flip();
		System.out.println(revBuffer.limit());
		System.out.println(new String(revBuffer.array(),0,revBuffer.limit(),Charset.forName("GBK")));
		
		channel.close();
	}
	public static void main(String[] args) throws Exception 
	{
		//tcpClientWithoutSelector();//OK
		udpClientWithoutSelector();//OK
		
	}

}
