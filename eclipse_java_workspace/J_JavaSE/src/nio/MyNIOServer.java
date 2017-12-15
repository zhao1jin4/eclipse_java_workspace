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
		byteBuffer.array();//返回的数组没有变化,还是data中全部内容,
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
				 out.write(buf);//可能一次没有把buf中数据写完
				 buf.compact();//(因为前的数据已经写出,没用了)position=0,limit=capacity()
				 
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

		// 打开一个Selector,另一种方式 
		Selector acceptSelector = SelectorProvider.provider().openSelector();

		// 创建一个ServerSocketChannel，这是一个SelectableChannel的子类
		ServerSocketChannel ssc = ServerSocketChannel.open();

		// 将其设为non-blocking状态，这样才能进行异步IO操作
		ssc.configureBlocking(false);

		// 给ServerSocketChannel对应的socket绑定IP和端口
//		InetAddress lh = InetAddress.getLocalHost();
//		InetSocketAddress isa = new InetSocketAddress(lh, port);
		
		InetSocketAddress ip = new InetSocketAddress("localhost",12345);
		ssc.socket().bind(ip);

		// 将ServerSocketChannel注册到Selector上，返回对应的SelectionKey
		SelectionKey acceptKey =ssc.register(acceptSelector, SelectionKey.OP_ACCEPT);

		int keysAdded = 0;

		// 用select()函数来监控注册在Selector上的SelectableChannel

		// 返回值代表了有多少channel可以进行IO操作 (ready for IO)

		while ((keysAdded = acceptSelector.select()) > 0) {

			// selectedKeys()返回一个SelectionKey的集合，

			// 其中每个SelectionKey代表了一个可以进行IO操作的channel。

			// 一个ServerSocketChannel可以进行IO操作意味着有新的TCP连接连入了

			Set<SelectionKey> readyKeys = acceptSelector.selectedKeys();

			Iterator<SelectionKey> i = readyKeys.iterator();

			while (i.hasNext()) {

				SelectionKey sk =  i.next();//(SelectionKey)i.next();

				// 需要将处理过的key从selectedKeys这个集合中删除
				i.remove();

				// 从SelectionKey得到对应的channel
				ServerSocketChannel nextReady =(ServerSocketChannel) sk.channel();//因 register的是

				// 接受新的TCP连接
				Socket s = nextReady.accept().socket();//可以转成原始的Socket

				
				
				
				// 把当前的时间写到这个新的TCP连接中
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);

				Date now = new Date();
				out.println(now);

				//out.close();//结束与client连接
				//s.close();
			}

		}

	}

}
