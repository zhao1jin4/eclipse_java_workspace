package hadoop.avro.transfer;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder; 

import java.io.IOException;
import java.net.InetSocketAddress;

public class AvroServer {

    public static void main(String[] args) throws IOException {

        System.out.println("Starting avro server...");

//        Server nettyServer = new NettyServer(new SpecificResponder(DemoService.class,
//                new DemoServiceImpl()),
//                new InetSocketAddress(65111));
        //¶þÑ¡ Ò»
//
        Server saslSocketServer = new SaslSocketServer(new SpecificResponder(DemoService.class,
                new DemoServiceImpl()),
                new InetSocketAddress(10000));
 

        System.out.println("Avro erver started.");

        System.in.read();
    }
}