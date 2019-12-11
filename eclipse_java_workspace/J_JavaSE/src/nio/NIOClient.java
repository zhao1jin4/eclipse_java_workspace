package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 
public class NIOClient {
  static int SIZE = 1;
  static InetSocketAddress ip = new InetSocketAddress("localhost",12345);
  static CharsetEncoder encoder = Charset.forName("GB2312").newEncoder();
  static class Download implements Runnable {
    protected int index;
    public Download(int index) {
      this.index = index;
    }
    
    public void run() {
      try {
        long start = System.currentTimeMillis();
        SocketChannel client = SocketChannel.open();//这里也可传IP
        client.configureBlocking(false); //使用selector必须是false
        Selector selector = Selector.open();
        //Selector selector =SelectorProvider.provider().openSelector();//一样的
        
        client.register(selector, SelectionKey.OP_CONNECT);//客户端也不是用Server
        client.connect(ip);//连接后，服务器端的select()继续，isAcceptable
        ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
        int total = 0;
        FOR: for (;;) 
        {
	          selector.select();//第一次OP_CONNECT不阻塞,第二次写出后,要等务端读完,并已经有channel.write()
	          Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
	          while (iter.hasNext())
	          {
		            SelectionKey key = iter.next();
		            iter.remove();
		            if (key.isConnectable()) 
		            {
		              SocketChannel channel = (SocketChannel) key.channel();
		              if (channel.isConnectionPending())
		              		channel.finishConnect();//如服务端没有监听这里才出错
		              channel.write(encoder.encode(CharBuffer.wrap("Hello from " + index)));//写后服务端就可以读即select()不阻塞
		              channel.register(selector, SelectionKey.OP_READ);
		            } else if (key.isReadable()) 
		            {
				          SocketChannel channel = (SocketChannel) key.channel();
				          int count = channel.read(buffer); 
				          if (count > 0)
				          {
				        	buffer.flip();
				        	System.out.println("client_receive=:"+ Charset.forName("GB2312").decode(buffer));
				            total += count;
				            buffer.clear();
				          } else 
				          {
				            client.close();
				            break FOR;
				          }
		            }
	          }
        }
        double last = (System.currentTimeMillis() - start) * 1.0 / 1000;
        System.out.println("Thread " + index + " downloaded " + total
            + "bytes in " + last + "s.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws IOException {
	  

//	    Scanner scanner =new Scanner(System.in);
//	    while(scanner.hasNextLine())
//	    {
//	    	String line=scanner.nextLine();
//	    }
    ExecutorService exec = Executors.newFixedThreadPool(SIZE);
    for (int index = 0; index < SIZE; index++) {
      exec.execute(new Download(index));
    }
    exec.shutdown();
    
  }
}