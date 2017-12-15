package nio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MyNIOServer 
{
	public static void main(String[] args)
	{

		byte[] data1=new byte[]{48,48,48,56};
		ByteBuffer byteBuffer=  ByteBuffer.wrap(data1,2,2);
		byteBuffer.array();//���ص�����û�б仯,����data��ȫ������,
		byte[] dest=new byte[byteBuffer.position()] ;
		byteBuffer.get(dest);
	
		try
		{
			ByteBuffer buf=ByteBuffer.allocate(100);
			buf.clear();
			 FileChannel in=new FileInputStream ("c:/temp/file.txt").getChannel();
			 FileChannel out=new FileOutputStream ("c:/temp/fileOut.txt").getChannel();
			while (in.read(buf) >= 0 || buf.position() != 0)
			{
				 buf.flip();
				 out.write(buf);//����һ��û�а�buf������д��
				 buf.compact();//(��Ϊǰ�������Ѿ�д��,û����)position=0,limit=capacity()
				 
				int len = buf.position();
				 len =buf.limit();
				 len =buf.capacity();
				 
			}
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

		
		
		
		
		
		
		
		
		

		try {
			acceptConnections(12345);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void acceptConnections(int port) throws Exception {

		// ��һ��Selector,��һ�ַ�ʽ 
		Selector acceptSelector = SelectorProvider.provider().openSelector();

		// ����һ��ServerSocketChannel������һ��SelectableChannel������
		ServerSocketChannel ssc = ServerSocketChannel.open();

		// ������Ϊnon-blocking״̬���������ܽ����첽IO����
		ssc.configureBlocking(false);

		// ��ServerSocketChannel��Ӧ��socket��IP�Ͷ˿�
//		InetAddress lh = InetAddress.getLocalHost();
//		InetSocketAddress isa = new InetSocketAddress(lh, port);
		
		InetSocketAddress ip = new InetSocketAddress("localhost",12345);
		ssc.socket().bind(ip);

		// ��ServerSocketChannelע�ᵽSelector�ϣ����ض�Ӧ��SelectionKey
		SelectionKey acceptKey =ssc.register(acceptSelector, SelectionKey.OP_ACCEPT);

		int keysAdded = 0;

		// ��select()���������ע����Selector�ϵ�SelectableChannel

		// ����ֵ�������ж���channel���Խ���IO���� (ready for IO)

		while ((keysAdded = acceptSelector.select()) > 0) {

			// selectedKeys()����һ��SelectionKey�ļ��ϣ�

			// ����ÿ��SelectionKey������һ�����Խ���IO������channel��

			// һ��ServerSocketChannel���Խ���IO������ζ�����µ�TCP����������

			Set<SelectionKey> readyKeys = acceptSelector.selectedKeys();

			Iterator<SelectionKey> i = readyKeys.iterator();

			while (i.hasNext()) {

				SelectionKey sk =  i.next();//(SelectionKey)i.next();

				// ��Ҫ���������key��selectedKeys���������ɾ��
				i.remove();

				// ��SelectionKey�õ���Ӧ��channel
				ServerSocketChannel nextReady =(ServerSocketChannel) sk.channel();//�� register����

				// �����µ�TCP����
				Socket s = nextReady.accept().socket();//����ת��ԭʼ��Socket

				
				
				
				// �ѵ�ǰ��ʱ��д������µ�TCP������
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);

				Date now = new Date();
				out.println(now);

				//out.close();//������client����
				//s.close();
			}

		}

	}

}
