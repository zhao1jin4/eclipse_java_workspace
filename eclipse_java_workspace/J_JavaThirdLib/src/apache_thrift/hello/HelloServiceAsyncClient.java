package apache_thrift.hello;
import java.io.IOException; 
 import org.apache.thrift.async.AsyncMethodCallback; 
 import org.apache.thrift.async.TAsyncClientManager; 
 import org.apache.thrift.protocol.TBinaryProtocol; 
 import org.apache.thrift.protocol.TProtocolFactory; 
 import org.apache.thrift.transport.TNonblockingSocket; 
 import org.apache.thrift.transport.TNonblockingTransport; 

 public class HelloServiceAsyncClient { 
    /** 
     * 调用 Hello 服务
     * @param args 
     */ 
    public static void main(String[] args) throws Exception { 
        try { 
            TAsyncClientManager clientManager = new TAsyncClientManager(); 
            TNonblockingTransport transport = new TNonblockingSocket("localhost", 10005); 
            TProtocolFactory protocol = new TBinaryProtocol.Factory(); 
            Hello.AsyncClient asyncClient = new Hello.AsyncClient(protocol,clientManager, transport); 
            MethodCallback callBack = new MethodCallback(); 
            asyncClient.helloString("Hello World", callBack); 
            Object res = callBack.getResult(); 
            while (res == null) { 
                res = callBack.getResult(); 
                System.out.println("wait...."+res); 
            } 
//            System.out.println(res);  //用范型无结果？？？
            System.out.println( ((Hello.AsyncClient.helloString_call) res).getResult()); //如不用范型 <>
            
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
  } 
 }