package thrift.hello;
import org.apache.thrift.TException; 
import org.apache.thrift.protocol.TBinaryProtocol; 
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol; 
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TSocket; 
import org.apache.thrift.transport.TTransport; 
import org.apache.thrift.transport.TTransportException; 

 public class HelloServiceClient { 
 /** 
     * 调用 Hello 服务
     * @param args 
     */ 
    public static void main(String[] args) { 
        try { 
        
            //TTransport transport = new TSocket("localhost", 7911); 	//阻塞式
            TTransport transport = new TFramedTransport(new TSocket("localhost", 7911));//非阻塞式
            
            transport.open(); 

            
            // 设置传输协议为 TBinaryProtocol 
            //TProtocol protocol = new TBinaryProtocol(transport);
            TCompactProtocol protocol = new TCompactProtocol(transport);// 高效率的、密集的二进制编码格式进行数据传输 
//            TJSONProtocol protocol = new TJSONProtocol(transport);
            
            Hello.Client client = new Hello.Client(protocol); 
         
            // 调用服务的 helloVoid 方法
            client.helloVoid(); 
            
            String nullValue=client.helloNull();//返回null 报 TApplicationException，服务端也强迫关闭连接
            
            transport.close(); 
        } catch (TTransportException e) { 
            e.printStackTrace(); 
        } catch (TException e) { 
            e.printStackTrace(); 
        } 
    } 
 }