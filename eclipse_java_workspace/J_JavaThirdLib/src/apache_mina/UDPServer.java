package apache_mina;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

public class UDPServer {
	public static void main(String[] args) throws Exception
	{
		NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
		
		acceptor.setHandler(new MyServerHandler());
		DatagramSessionConfig dcfg = acceptor.getSessionConfig();
		dcfg.setReuseAddress(true);
		acceptor.bind(new InetSocketAddress("localhost",12312 ));
	} 

}


class MyServerHandler extends IoHandlerAdapter
{
	@Override
	public void sessionCreated(IoSession session) throws Exception
	{
		  SocketAddress remoteAddress = session.getRemoteAddress();
		  System.out.println("client:"+remoteAddress+" connected");
		  
	}
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof IoBuffer)
		{
	        IoBuffer buffer = (IoBuffer) message;
	        System.out.println("receive msg:"+  buffer.getLong());
	        SocketAddress remoteAddress = session.getRemoteAddress();
	        
	    }
	}
}