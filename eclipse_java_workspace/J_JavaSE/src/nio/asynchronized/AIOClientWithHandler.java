package nio.asynchronized;
import java.net.InetSocketAddress; 
import java.nio.ByteBuffer; 
import java.nio.channels.AsynchronousSocketChannel; 
import java.nio.channels.CompletionHandler; 
 
public class AIOClientWithHandler { 
    private final AsynchronousSocketChannel client ; 
     
    public AIOClientWithHandler() throws Exception{ 
       client = AsynchronousSocketChannel.open(); 
    } 
     
    public void start()throws Exception
    {
    	//attachment Ϊnull
        client.connect(new InetSocketAddress("127.0.0.1",8888),null,new CompletionHandler<Void,Void>()
        { 
            @Override 
            public void completed(Void result, Void attachment) 
            { 
                try { 
                    Integer writed=client.write(ByteBuffer.wrap("test".getBytes())).get();
                    System.out.println("client writed bytes:"+writed);
                    
                    ByteBuffer buf=ByteBuffer.allocate(1024);
                    int readed =  client.read(buf).get();
                    System.out.println("client receive data : "+new String(buf.array()));
                } catch (Exception ex) { 
                    ex.printStackTrace(); 
                } 
            } 
 
            @Override 
            public void failed(Throwable exc, Void attachment) { 
                exc.printStackTrace(); 
            } 
        }); 
        Thread.sleep(200);
        client.close();
        
    } 
     
    public static void main(String args[])throws Exception
    { 
        new AIOClientWithHandler().start(); 
    } 
}  