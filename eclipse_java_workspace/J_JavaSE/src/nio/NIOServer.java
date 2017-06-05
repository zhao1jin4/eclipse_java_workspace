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
 * һ��������ͨ�����Ա�ע�ᵽ����һ����ѡ�����ϣ����Ҳ���Ҫ֪������ע�����Ǹ�Selector������ ,      channel.isRegistered( )
 * 
 * ��һ��ͨ��ע�ᵽ����һ����ѡ����������ġ���ô���Ļ����ڸ���interest����Ϊָ����ֵ��ͬʱ����������֮ǰ��ͬ��ѡ�����
 * ʵ���ϣ�������ע�ᶼֻ�Ǽ򵥵ؽ���֮ǰ��ע���ϵ��صļ����и��£���4.2С�ڣ�
 * 
 * һ������������ǵ�����ͼ��һ��ͨ��ע�ᵽһ����صļ��Ѿ���ȡ����ѡ�����ϣ���ͨ����Ȼ���ڱ�ע���״̬��ʱ��
 * ͨ�������ڼ���ȡ����ʱ������ע����ֱ����һ�β�������Ϊֹ��������Ȼ�ᴦ�ڱ�ע���״̬(��4.3С��)��
 * ����������£�δ����CancelledKeyException���ᱻ�׳���������ڼ����ܱ�ȡ��������¼��SelectionKey�����״̬��
 * 
 *  key.isValid();//��selector.close(),channel.close(),selectionKey.cancel();
	key.cancel();//��������ص�ѡ��������ȡ���ļ��ļ����ע�᲻��������ȡ��,����������ʧЧ
				���ٴε���select( )����ʱ������һ�����ڽ��е�select()���ý���ʱ������ȡ���ļ��ļ����еı�ȡ���ļ������������������Ӧ��ע��Ҳ����ɡ�ͨ���ᱻע�������µ�SelectionKey�������ء�
	
	
	һ��ͨ����ѡ������ע���ϵ������װ��һ��SelectionKey
	channel.keyFor(sel);//����ע�ᵽselector�ϵ�SelectionKey,û�з���null
	selectionKey.channel();
	selectionKey.selector();
	
	
	��ͨ���ر�ʱ��������صļ����Զ�ȡ������ס��һ��ͨ�����Ա�ע�ᵽ���ѡ�����ϣ�
	 ��ѡ�����ر�ʱ�����б�ע�ᵽ��ѡ������ͨ��������ע����������صļ�����������Ч����ȡ����
	 
	 
	 
 	һ������ָʾ��Щͨ��/ѡ��������������ĵĲ���(instrest����)����һ����ʾͨ��׼����Ҫִ�еĲ�����ready���ϣ���
	 ��ǰ��interest���Ͽ���ͨ�����ü������interestOps( )��������ȡ��
	 �������Ӧ����ͨ����ע��ʱ��������ֵ�����interset������Զ���ᱻѡ�����ı䣬��������ͨ������interestOps( )����������һ���µı�������������ı���
	 ����ص�Selector�ϵ�select( )�������ڽ���ʱ�ı����interest���ϣ�����Ӱ���Ǹ����ڽ��е�ѡ����������и��Ľ�����select( )����һ�����������ֳ���
	
	readyOps( )��������ȡ��ص�ͨ�����Ѿ������Ĳ�����ready������interest���ϵ��Ӽ������ұ�ʾ��interest�����д��ϴε���select( )�����Ѿ���������Щ��
	
	
	
	������߳�ʹ�õ�ѡ����Ҳֻ����ϵͳ�ر���ʱ��������
	
	
	selector.keys()�����Ѿ�ע���SelectionKey���������޸�
	
	Selector���󽫲�׽InterruptedException�쳣������wakeup( )������
 */
public class NIOServer {
  static int BLOCK = 4096;
  
  
  // ������ͻ��˵Ľ���
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
	   
    selector = this.getSelector(port);//ֻҪSelector��OK
    Charset charset = Charset.forName("GB2312");
    decoder = charset.newDecoder();
  }

  // ��ȡSelector
  protected Selector getSelector(int port) throws IOException {
    ServerSocketChannel server = ServerSocketChannel.open();
    server.bind(new InetSocketAddress(port));
    //Selector sel = Selector.open();
    
    Selector sel =SelectorProvider.provider().openSelector();//һ����
   // server.socket().bind(new InetSocketAddress(port));

    server.configureBlocking(false); 
    server.register(sel, SelectionKey.OP_ACCEPT);
    // interest set   ,��������Ҳ���Դ�attachement
    int allBit=server.validOps();//��������֧�ֵ�SelectionKey.OP_X
    return sel;
  }

  // �����˿�
  public void listen() 
  {
    try 
    {
   for (;;){ 
        selector.select();//�����Ѿ�ready�ĸ��� , ����ָ������ʱ�䣬�����ʱ����� selector.wakeup,������,�ֹ�����wakeup() ����Ե���selectNow()
        //��server��OP_ACCEPT,����������ֱ��client��connect(ip)
        //��server��OP_READ��ֱ��client��channel.write()
        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) 
        {
          SelectionKey key = iter.next();//SelectionKey���̰߳�ȫ��
          iter.remove(); 
          handleKey(key); 
        }
    }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // �����¼�
  protected void handleKey(SelectionKey key) throws IOException {
    if (key.isAcceptable()) // ��������,ֻ��ServerSocketChannel֧�֣��� validOps ����
    { //(readyOps() & OP_ACCEPT) != 0;
System.out.println("��������");
      ServerSocketChannel server = (ServerSocketChannel) key.channel();
      SocketChannel channel = server.accept();
      channel.configureBlocking(false);//ʹ��selector������false
      channel.register(selector, SelectionKey.OP_READ);
    
    } else if (key.isReadable())// ����Ϣ
    {
System.out.println("����Ϣ");
      SocketChannel channel = (SocketChannel) key.channel();//�ϲ�ע�������,server��ֻҪ��client������OK
      int count = channel.read(clientBuffer);
      if (count > 0) {
        clientBuffer.flip();
        CharBuffer charBuffer = decoder.decode(clientBuffer);
        System.out.println("Client >>" + charBuffer.toString());
        SelectionKey wKey = channel.register(selector, SelectionKey.OP_WRITE);
        wKey.attach(new HandleClient());//�ɴ����󸽼�,��Ϊ ��  �˵�������(read,write)ʹ��
      } else
        channel.close();
      clientBuffer.clear();
    } else if (key.isWritable()) // д�¼�
    {
System.out.println("д�¼�");
      SocketChannel channel = (SocketChannel) key.channel();
      HandleClient handle = (HandleClient) key.attachment();//�õ�����
      ByteBuffer block = handle.readBlock();//�ļ���,Ҫ������
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