package nio;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

/**
 * 一个给定的通道可以被注册到多于一个的选择器上，而且不需要知道它被注册了那个Selector对象上 ,      channel.isRegistered( )
 * 
 * 将一个通道注册到多于一个的选择器上允许的。这么做的话，在更新interest集合为指定的值的同时，将返回与之前相同的选择键。
 * 实际上，后续的注册都只是简单地将与之前的注册关系相关的键进行更新（见4.2小节）
 * 
 * 一个例外的情形是当您试图将一个通道注册到一个相关的键已经被取消的选择器上，而通道仍然处于被注册的状态的时候。
 * 通道不会在键被取消的时候立即注销。直到下一次操作发生为止，它们仍然会处于被注册的状态(见4.3小节)。
 * 在这种情况下，未检查的CancelledKeyException将会被抛出。请务必在键可能被取消的情况下检查SelectionKey对象的状态。
 * 
 *  key.isValid();//当selector.close(),channel.close(),selectionKey.cancel();
	key.cancel();//被放在相关的选择器的已取消的键的集合里。注册不会立即被取消,但键会立即失效
				当再次调用select( )方法时（或者一个正在进行的select()调用结束时），已取消的键的集合中的被取消的键将被清理掉，并且相应的注销也将完成。通道会被注销，而新的SelectionKey将被返回。
	
	
	一个通道和选择器的注册关系都被封装在一个SelectionKey
	channel.keyFor(sel);//返回注册到selector上的SelectionKey,没有返回null
	selectionKey.channel();
	selectionKey.selector();
	
	
	当通道关闭时，所有相关的键会自动取消（记住，一个通道可以被注册到多个选择器上）
	 当选择器关闭时，所有被注册到该选择器的通道都将被注销，并且相关的键将立即被无效化（取消）
	 
	 
	 
 	一个用于指示那些通道/选择器组合体所关心的操作(instrest集合)，另一个表示通道准备好要执行的操作（ready集合）。
	 当前的interest集合可以通过调用键对象的interestOps( )方法来获取。
	 最初，这应该是通道被注册时传进来的值。这个interset集合永远不会被选择器改变，但您可以通过调用interestOps( )方法并传入一个新的比特掩码参数来改变它
	 当相关的Selector上的select( )操作正在进行时改变键的interest集合，不会影响那个正在进行的选择操作。所有更改将会在select( )的下一个调用中体现出来
	
	readyOps( )方法来获取相关的通道的已经就绪的操作。ready集合是interest集合的子集，并且表示了interest集合中从上次调用select( )以来已经就绪的那些操
	
	
	
	被多个线程使用的选择器也只会在系统特别复杂时产生问题
	
	
	selector.keys()返回已经注册的SelectionKey，但不能修改
	
	Selector对象将捕捉InterruptedException异常并调用wakeup( )方法。
 */
public class NIOServer {
  static int BLOCK = 4096;
  
  
  // 处理与客户端的交互
  public class HandleClient 
  {
    protected FileChannel channel;
    protected ByteBuffer buffer;
    public HandleClient() throws IOException 
    {
      this.channel = new FileInputStream(filename).getChannel();
      this.buffer = ByteBuffer.allocate(BLOCK);
    }
    public ByteBuffer readBlock() {
      try {
        buffer.clear();
        int count = channel.read(buffer);
        buffer.flip();
        if (count <= 0)
          return null;
      } catch (IOException e) {
        e.printStackTrace();
      }
      return buffer;
    }
    public void close() {
      try {
        channel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }//class

  protected Selector selector;
  protected String filename = "c:/temp/file.txt"; // a big file
  protected ByteBuffer clientBuffer = ByteBuffer.allocate(BLOCK);
  protected CharsetDecoder decoder;

  public NIOServer(int port) throws IOException {
	   
    selector = this.getSelector(port);//只要Selector就OK
    Charset charset = Charset.forName("GB2312");
    decoder = charset.newDecoder();
  }

  // 获取Selector
  protected Selector getSelector(int port) throws IOException {
    ServerSocketChannel server = ServerSocketChannel.open();
    server.bind(new InetSocketAddress(port));
    //Selector sel = Selector.open();
    
    Selector sel =SelectorProvider.provider().openSelector();//一样的
   // server.socket().bind(new InetSocketAddress(port));

    server.configureBlocking(false); 
    server.register(sel, SelectionKey.OP_ACCEPT);
    // interest set   ,三个参数也可以传attachement
    int allBit=server.validOps();//返回所有支持的SelectionKey.OP_X
    return sel;
  }

  // 监听端口
  public void listen() 
  {
    try 
    {
   for (;;){ 
        selector.select();//返回已经ready的个数 , 可以指定毫秒时间，如果到时间调用 selector.wakeup,不阻塞,手工调用wakeup() 后可以调用selectNow()
        //如server是OP_ACCEPT,这里会堵塞，直到client用connect(ip)
        //如server是OP_READ，直到client用channel.write()
        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) 
        {
          SelectionKey key = iter.next();//SelectionKey是线程安全的
          iter.remove(); 
          handleKey(key); 
        }
    }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // 处理事件
  protected void handleKey(SelectionKey key) throws IOException {
    if (key.isAcceptable()) // 接收请求,只有ServerSocketChannel支持，看 validOps 方法
    { //(readyOps() & OP_ACCEPT) != 0;
System.out.println("接收请求");
      ServerSocketChannel server = (ServerSocketChannel) key.channel();
      SocketChannel channel = server.accept();
      channel.configureBlocking(false);//使用selector必须是false
      channel.register(selector, SelectionKey.OP_READ);
    
    } else if (key.isReadable())// 读信息
    {
System.out.println("读信息");
      SocketChannel channel = (SocketChannel) key.channel();//上步注册的类型,server端只要对client操作就OK
      int count = channel.read(clientBuffer);
      if (count > 0) {
        clientBuffer.flip();
        CharBuffer charBuffer = decoder.decode(clientBuffer);
        System.out.println("Client >>" + charBuffer.toString());
        SelectionKey wKey = channel.register(selector, SelectionKey.OP_WRITE);
        wKey.attach(new HandleClient());//可传对象附件,可为 本  端的其它步(read,write)使用
      } else
        channel.close();
      clientBuffer.clear();
    } else if (key.isWritable()) // 写事件
    {
System.out.println("写事件");
      SocketChannel channel = (SocketChannel) key.channel();
      HandleClient handle = (HandleClient) key.attachment();//得到附件
      ByteBuffer block = handle.readBlock();//文件大,要读几次
      if (block != null)
        channel.write(block);
      else {
        handle.close();
        channel.close();
      }
    }
  }

  public static void main(String[] args) {
    int port = 12345;
    try {
      NIOServer server = new NIOServer(port);
      System.out.println("Listernint on " + port);
//      while (true) {
        server.listen();
//      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}