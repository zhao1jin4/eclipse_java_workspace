package apache_mina.example;
 
   import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
   
   /**
    * (<strong>Entry Point</strong>) Starts SumUp client.
    *
    * @author <a href="http://mina.apache.org">Apache MINA Project</a>
    */
   public class Client {
       private static final String HOSTNAME = "localhost";
   
       private static final int PORT = 8080;
   
       private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds
   
       // Set this to false to use object serialization instead of custom codec.
       private static final boolean USE_CUSTOM_CODEC = false;
   
       public static void main(String[] args) throws Throwable {
           if (args.length == 0) {
               System.out.println("Please specify the list of any integers");
               return;
           }
   
           // prepare values to sum up
           int[] values = new int[args.length];
           for (int i = 0; i < args.length; i++) {
               values[i] = Integer.parseInt(args[i]);
           }
   
           NioSocketConnector connector = new NioSocketConnector();
   
           // Configure the service.
           connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
           if (USE_CUSTOM_CODEC) {
               connector.getFilterChain().addLast(
                       "codec",
                       new ProtocolCodecFilter(
                               new SumUpProtocolCodecFactory(false)));
           } else {
               connector.getFilterChain().addLast(
                       "codec",
                       new ProtocolCodecFilter(
                               new ObjectSerializationCodecFactory()));
           }
           connector.getFilterChain().addLast("logger", new LoggingFilter());
   
           connector.setHandler(new ClientSessionHandler(values));
   
           IoSession session;
           for (;;) {
               try {
                   ConnectFuture future = connector.connect(new InetSocketAddress(
                           HOSTNAME, PORT));
                   future.awaitUninterruptibly();
                   session = future.getSession();
                   break;
               } catch (RuntimeIoException e) {
                   System.err.println("Failed to connect.");
                   e.printStackTrace();
                   Thread.sleep(5000);
               }
           }
           // wait until the summation is done
           session.getCloseFuture().awaitUninterruptibly();
           
           connector.dispose();
       }
   }