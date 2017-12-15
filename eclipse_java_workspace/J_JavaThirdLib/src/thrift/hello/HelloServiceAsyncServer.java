package thrift.hello;
 import org.apache.thrift.server.TNonblockingServer; 
 import org.apache.thrift.server.TServer; 
 import org.apache.thrift.transport.TNonblockingServerSocket; 
 import org.apache.thrift.transport.TNonblockingServerTransport; 
 import org.apache.thrift.transport.TTransportException; 

 public class HelloServiceAsyncServer { 
    /** 
     * 启动 Thrift 异步服务器
     * @param args 
     */ 
    public static void main(String[] args) { 
        TNonblockingServerTransport serverTransport; 
        try { 
            serverTransport = new TNonblockingServerSocket(10005); 
            Hello.Processor<HelloServiceImpl> processor = new Hello.Processor<HelloServiceImpl>( 
                    new HelloServiceImpl()); 
            TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor) ); 
            System.out.println("Start server on port 10005 ..."); 
            server.serve(); 
        } catch (TTransportException e) { 
            e.printStackTrace(); 
        } 
    } 
 }