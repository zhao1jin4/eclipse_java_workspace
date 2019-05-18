package rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.RpcClient;
/*
public class MyRpcClient extends  RpcClient{

	public MyRpcClient(Channel channel, String exchange, String routingKey, String replyTo, int timeout)
			throws IOException {
		super(channel, exchange, routingKey, replyTo, timeout);
		 
	}

	@Override
	public Response doCall(BasicProperties props, byte[] message) throws IOException, TimeoutException {
		 
		return super.doCall(props, message);
	}

}
*/


