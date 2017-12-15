package thrift.hello;
import org.apache.thrift.TProcessor; 
import org.apache.thrift.protocol.TBinaryProtocol; 
import org.apache.thrift.protocol.TBinaryProtocol.Factory; 
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer; 
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer; 
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket; 
import org.apache.thrift.transport.TTransportException; 

 public class HelloServiceServer { 
    /** 
     * 启动 Thrift 服务器
     * @param args 
     */ 
    public static void main(String[] args) { 
        try { 
           
            
            // 设置协议工厂为 TBinaryProtocol.Factory 
           //Factory proFactory = new TBinaryProtocol.Factory(); 
          TCompactProtocol.Factory proFactory = new TCompactProtocol.Factory();// 高效率的、密集的二进制编码格式进行数据传输 
          //TJSONProtocol.Factory proFactory = new TJSONProtocol.Factory();
           
            // 关联处理器与 Hello 服务的实现
            TProcessor processor = new Hello.Processor(new HelloServiceImpl()); 
            
            
            //阻塞式
//            TServerSocket serverTransport = new TServerSocket(7911); 
//            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor)
//            					.protocolFactory(proFactory));
//          TServer server = new TSimpleServer(new TSimpleServer.Args(serverTransport).processor(processor)
//            					.protocolFactory(proFactory));//单线程
            
          //非阻塞式
       	  TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(7911); 
          TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor)
           		 	.protocolFactory(proFactory)); 
            
            System.out.println("Start server on port 7911..."); 
            server.serve(); 
        } catch (TTransportException e) { 
            e.printStackTrace(); 
        } 
        
    } 
 }