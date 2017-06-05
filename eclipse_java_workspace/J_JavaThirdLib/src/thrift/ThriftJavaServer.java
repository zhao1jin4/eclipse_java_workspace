package thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class ThriftJavaServer {

	public static void main(String[] args) {
	   try {
		      TServerTransport serverTransport = new TServerSocket(9090);
		      TServer server = new TSimpleServer(new TSimpleServer.Args(serverTransport).processor(processor));
	
		      // Use this for a multithreaded server
		       TServer server1 = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
	
		      System.out.println("Starting the simple server...");
		      server.serve();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}

}
