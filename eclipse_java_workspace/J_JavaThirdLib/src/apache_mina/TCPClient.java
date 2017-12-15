package apache_mina;

import java.net.InetSocketAddress;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.firewall.BlacklistFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TCPClient {

	public static String sessionCountKey="count";
	public static void main(String[] args) throws Throwable 
	{
		
	    NioSocketConnector connector = new NioSocketConnector();
	    connector.setConnectTimeoutMillis(3000);
	    
	    //connector.getFilterChain().addLast("black",  new BlacklistFilter());
	    connector.getFilterChain().addLast("logger", new LoggingFilter());
	    //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
	    connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
	
	    
	    connector.setHandler(new ClientSessionHandler());//�ص���

	    connector.getManagedSessions();
	    
        ConnectFuture future = connector.connect(new InetSocketAddress(9123));
        future.addListener( new IoFutureListener<ConnectFuture>()
		{
            public void operationComplete(ConnectFuture future) //���һ��ִ��
            {
                if( future.isConnected() )
                {
                	IoSession session =future.getSession();
                    IoBuffer buffer = IoBuffer.allocate(8);
                    //buffer.putLong(85);
                    buffer.putChar('L');
                    buffer.putChar('E');
                    buffer.putChar('N');
                    buffer.flip();
                    session.write(buffer);//��һ�˲��������յ�����Ҫ���ⲿ��session.write��д
                } else {
                   System.out.println("Not connected...exiting");
                }
            }
        });
        future.awaitUninterruptibly();
        IoSession    session = future.getSession();
        session.setAttribute(sessionCountKey,0);//sessionֻ�����Լ���һ�˿��Է���
        
        WriteFuture write= session.write("�ͻ��˵ڶ���дHeader");//����ſ�ʼдConnectFuture�� Listener��session.write �ٺ����� һ�𵽷���˵�
        write.addListener(new IoFutureListener<IoFuture>() 
        {
			@Override
			public void operationComplete(IoFuture fure) {
				System.out.println("�ͻ��˵ڶ���дHeader���");
			}
		}) ;

	    // wait until the summation is done
	    session.getCloseFuture().awaitUninterruptibly();//�裬��һ�˹ر�ʱ����ر�
	    connector.dispose();
	}

}


class ClientSessionHandler extends IoHandlerAdapter
{
	public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
	{
	    cause.printStackTrace();
	}
	public void sessionCreated(IoSession session) throws Exception {//�� sessionCreated-> �� sessionOpened
		 System.out.println("sessionCreated");
	}
	public void sessionOpened(IoSession session) throws Exception {
		 System.out.println("sessionOpened:"+session.getId());
	}
	public void messageReceived( IoSession session, Object message ) throws Exception
	{
		int count=Integer.parseInt(session.getAttribute(TCPClient.sessionCountKey).toString());
		session.setAttribute(TCPClient.sessionCountKey , ++count);
		if(count > 3)
		{
			session.write("quit");
			return;
		}else
		{
			String str = message.toString();
		    System.out.println("�ͻ��� receive is:"+str);
		    session.write("hello ��ã�");//���ﲻ����������,��һ���������ִ����ɲŻ��յ�
		    System.out.println("�ͻ����Ѿ�д��hello");
		}
	  
	}
	public void messageSent(IoSession session, Object message) throws Exception {//������write�����������,��һ������
		 System.out.println("messageSent: session="+session.getId()+",message="+message);
	}
	public void sessionClosed(IoSession session) throws Exception {
		 System.out.println("sessionClosed");
	}
	public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
	{
//	    System.out.println( "IDLE " + session.getIdleCount( status ));
	}
}