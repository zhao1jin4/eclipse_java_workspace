/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package apache_mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * A minimal 'time' server, returning the current date. Opening
 * a telnet server, you will get the current date by typing
 * any string followed by a new line.
 * 
 * In order to quit, just send the 'quit' message.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class TCPServer {
    /** We will use a port above 1024 to be able to launch the server with a standard user */
    private static final int PORT = 9123;

    /**
     * The server implementation. It's based on TCP, and uses a logging filter 
     * plus a text line decoder.
     */
    public static void main(String[] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        //acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));

        acceptor.setHandler( new TimeServerHandler() );

        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );//空闲10秒后调用Handler sessionIdle方法 
        
        acceptor.bind( new InetSocketAddress(PORT));
    }
}


class TimeServerHandler extends IoHandlerAdapter
{
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
    }
	public void sessionCreated(IoSession session) throws Exception {//先 sessionCreated-> 再 sessionOpened
		 System.out.println("sessionCreated");
	}
	public void sessionOpened(IoSession session) throws Exception {
		 System.out.println("sessionOpened:"+session.getId());
	}

	public void messageReceived( IoSession session, Object message ) throws Exception
    {
        String str = message.toString();
        System.out.println("Server receive is:"+str);
        if( str.trim().equalsIgnoreCase("quit") ) 
        {
            session.closeNow();
            return;
        }
        Date date = new Date();
        session.write( date.toString() );//这里不会立即发送,另一端这个方法执行完成才会收到
        System.out.println("Message written...");
    }

	public void messageSent(IoSession session, Object message) throws Exception {//已经发送以后调用这个方法
		 System.out.println("messageSent: session="+session.getId()+",message="+message);
	}
	public void sessionClosed(IoSession session) throws Exception {
		 System.out.println("sessionClosed");
	}
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
//        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
}
