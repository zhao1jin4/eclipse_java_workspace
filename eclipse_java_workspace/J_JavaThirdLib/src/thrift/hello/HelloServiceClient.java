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
     * ���� Hello ����
     * @param args 
     */ 
    public static void main(String[] args) { 
        try { 
        
            //TTransport transport = new TSocket("localhost", 7911); 	//����ʽ
            TTransport transport = new TFramedTransport(new TSocket("localhost", 7911));//������ʽ
            
            transport.open(); 

            
            // ���ô���Э��Ϊ TBinaryProtocol 
            //TProtocol protocol = new TBinaryProtocol(transport);
            TCompactProtocol protocol = new TCompactProtocol(transport);// ��Ч�ʵġ��ܼ��Ķ����Ʊ����ʽ�������ݴ��� 
//            TJSONProtocol protocol = new TJSONProtocol(transport);
            
            Hello.Client client = new Hello.Client(protocol); 
         
            // ���÷���� helloVoid ����
            client.helloVoid(); 
            
            String nullValue=client.helloNull();//����null �� TApplicationException�������Ҳǿ�ȹر�����
            
            transport.close(); 
        } catch (TTransportException e) { 
            e.printStackTrace(); 
        } catch (TException e) { 
            e.printStackTrace(); 
        } 
    } 
 }