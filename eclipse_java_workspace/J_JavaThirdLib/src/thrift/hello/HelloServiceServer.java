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
     * ���� Thrift ������
     * @param args 
     */ 
    public static void main(String[] args) { 
        try { 
           
            
            // ����Э�鹤��Ϊ TBinaryProtocol.Factory 
           //Factory proFactory = new TBinaryProtocol.Factory(); 
          TCompactProtocol.Factory proFactory = new TCompactProtocol.Factory();// ��Ч�ʵġ��ܼ��Ķ����Ʊ����ʽ�������ݴ��� 
          //TJSONProtocol.Factory proFactory = new TJSONProtocol.Factory();
           
            // ������������ Hello �����ʵ��
            TProcessor processor = new Hello.Processor(new HelloServiceImpl()); 
            
            
            //����ʽ
//            TServerSocket serverTransport = new TServerSocket(7911); 
//            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor)
//            					.protocolFactory(proFactory));
//          TServer server = new TSimpleServer(new TSimpleServer.Args(serverTransport).processor(processor)
//            					.protocolFactory(proFactory));//���߳�
            
          //������ʽ
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