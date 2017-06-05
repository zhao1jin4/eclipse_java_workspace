package nio.asynchronized;
import java.io.IOException; 
import java.net.InetSocketAddress; 
import java.nio.ByteBuffer; 
import java.nio.channels.AsynchronousSocketChannel; 
import java.util.concurrent.ExecutionException; 
import java.util.concurrent.Future;
 
public class AIOClientWithFuture { 
    private final AsynchronousSocketChannel client; 
     
    public AIOClientWithFuture() throws IOException{ 
        client = AsynchronousSocketChannel.open(); 
    } 
     
    public void sendMsg() throws  Exception { 
        Future<Void> future=client.connect(new InetSocketAddress("127.0.0.1",8888)); 
        future.get();
        System.out.println("\n connected");
       int writed= client.write(ByteBuffer.wrap("test".getBytes())).get(); 
        System.out.println("client send data length:"+writed);
       
        ByteBuffer buf=ByteBuffer.allocate(1024);
        int readed =  client.read(buf).get();
        System.out.println("client receive data : "+new String(buf.array()));
        client.close();
    }
    public static void main(String...args) throws Exception{ 
        AIOClientWithFuture client = new AIOClientWithFuture(); 
        client.sendMsg(); 
    } 
}  