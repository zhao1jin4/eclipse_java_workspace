 
package netty41_my;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
 
// 	SimpleChannelInboundHandler
public class MyServerHandler extends ChannelInboundHandlerAdapter {//In是读， 对应的有Out
 	//一般要重写这几个方法
	@Override
    public void channelActive(ChannelHandlerContext ctx) {
//        ctx.writeAndFlush(firstMessage);//客户端用
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	ByteBuf buf=(ByteBuf)msg;
    	System.out.println("服务收到："+buf.toString(CharsetUtil.UTF_8));
    	
//        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
    	ctx.writeAndFlush(Unpooled.copiedBuffer("服务测试消息".getBytes(CharsetUtil.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
