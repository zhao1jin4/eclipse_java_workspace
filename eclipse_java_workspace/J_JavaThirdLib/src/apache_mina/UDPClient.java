package apache_mina;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

public class UDPClient {
	public static void main(String[] args)
	{
		NioDatagramConnector connector = new NioDatagramConnector();
		connector.setHandler( new MyClientHandler() );
		ConnectFuture connFuture = connector.connect( new InetSocketAddress("localhost",12312 ));
		connFuture.addListener( new IoFutureListener<ConnectFuture>()
		{
            public void operationComplete(ConnectFuture future) 
            {
                if( future.isConnected() )
                {
                	IoSession session =future.getSession();
                     IoBuffer buffer = IoBuffer.allocate(8);
                     buffer.putLong(23);
                     buffer.flip();
                     session.write(buffer);
                    
                } else {
                   System.out.println("Not connected...exiting");
                }
            }
        });
		
	}
}

class MyClientHandler extends IoHandlerAdapter
{
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		  SocketAddress remoteAddress = session.getRemoteAddress();
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof IoBuffer) {
	        IoBuffer buffer = (IoBuffer) message;
	        SocketAddress remoteAddress = session.getRemoteAddress();
	          buffer.getLong();
	    }
	}
}