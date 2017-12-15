package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

public class NIOReceiveHttpServer 
{
	public static void main(String[] args) throws Exception 
	{
		Charset charset = Charset.forName("GBK");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		ByteBuffer clientBuffer = ByteBuffer.allocate(4096 * 100);

		ServerSocketChannel server = ServerSocketChannel.open();
		Selector selector = Selector.open();
		server.socket().bind(new InetSocketAddress(8888));
		System.out.println(" NIO �����˿� 8888");
		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);

		while(true)
		{
			selector.select();
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			while (iter.hasNext())
			{
				SelectionKey key = iter.next();
				iter.remove();
	
				if (key.isAcceptable()) // ��������
				{
					System.out.println("��������");
					ServerSocketChannel server1 = (ServerSocketChannel) key
							.channel();
					SocketChannel channel = server1.accept();
					channel.configureBlocking(false);
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable())// ����Ϣ
				{
					System.out.println("����Ϣ");
					SocketChannel channel = (SocketChannel) key.channel();
					int count = channel.read(clientBuffer);
					if (count > 0) 
					{
						clientBuffer.flip();
						CharBuffer charBuffer = decoder.decode(clientBuffer);
						System.out.println("------------Client ------------");//--Post�ļ��ϴ�  Ӧ����ʲô�ָ���
						//soapUI POST����OK,���յ� http body
						 //JMeter Get���� ,�����յ� http body
						System.out.println(charBuffer.toString()); 
						clientBuffer.clear();
						SelectionKey wKey = channel.register(selector,SelectionKey.OP_WRITE);
						// wKey.attach(new HandleClient());//���Ӹ���
					}
				} else if (key.isWritable()) // д�¼�
				{
					System.out.println("д�¼�");
					
					StringBuffer header =new StringBuffer();
					
					header
					.append("HTTP/1.1 200 OK\n")
					.append("Content-Type:text/html\n") 
					 .append("Connection:Keep-Alive\n")
					 .append("Keep-Alive:timeout=20\n")
					 .append("Cache-Control:private, max-age=0, must-revalidate\n")
					 .append("\n")
					 .append("<result><code>200</code><description>�ɹ�</description></result>");	
					
					ByteBuffer outBuffer=	ByteBuffer.wrap(header.toString().getBytes() );
					
					SocketChannel channel = (SocketChannel) key.channel();
					channel.write(outBuffer);
					channel.close();
				}
			}

		}
	}
}
