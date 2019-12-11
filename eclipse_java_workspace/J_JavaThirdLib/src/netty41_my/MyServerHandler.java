 
package netty41_my;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
 
// 	SimpleChannelInboundHandler
public class MyServerHandler extends ChannelInboundHandlerAdapter {//In�Ƕ��� ��Ӧ����Out
 	//һ��Ҫ��д�⼸������
	@Override
    public void channelActive(ChannelHandlerContext ctx) {
//        ctx.writeAndFlush(firstMessage);//�ͻ�����
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	ByteBuf buf=(ByteBuf)msg;
    	System.out.println("�����յ���"+buf.toString(CharsetUtil.UTF_8));
    	
//        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
    	ctx.writeAndFlush(Unpooled.copiedBuffer("���������Ϣ".getBytes(CharsetUtil.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
