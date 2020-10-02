package apache_rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class AsyncProducer {
    public static void main(String[] args) throws Exception {
    	String mqGroup = "myGroup";
		String mqIP = "localhost:9876";
		String mqTopic = "myTopic";
		String mqTag = "myTag";

		String key="123";
		String msgStr="hello 小李";
		
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(mqGroup);
        producer.setNamesrvAddr(mqIP);
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 10; i++) {
                final int index = i;
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message(mqTopic, mqTag, key,
                    msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() { //异步发
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                    }
                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}

