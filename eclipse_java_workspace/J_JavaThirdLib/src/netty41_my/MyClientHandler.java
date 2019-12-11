 
package netty41_my;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
 
//extends SimpleChannelInboundHandler
public class MyClientHandler extends ChannelInboundHandlerAdapter {
	//In�Ƕ�����Ӧ����ChannelOutboundHandlerAdapter
   
	//һ��Ҫ��д�⼸������
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("�ͻ�������Ϣ".getBytes(CharsetUtil.UTF_8)));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
       // ctx.write(msg);
    	ByteBuf buf=(ByteBuf)msg;
    	System.out.println("�ͻ��յ���"+buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
