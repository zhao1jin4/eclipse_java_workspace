package apache_rocketmq;

import java.util.Date;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {
    public static void main(String[] args) throws Exception {
		String mqGroup = "myGroup";
		String mqIP = "localhost:9876";
		String mqTopic = "myTopic";
		String mqTag = "myTag";

		String msgStr="hello 小李";
		// bin/mqbroker -n localhost:9876  autoCreateTopicEnable=true
		
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new  DefaultMQProducer(mqGroup);
        producer.setNamesrvAddr(mqIP);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 1; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(mqTopic,
            		mqTag,
                (msgStr + i).getBytes(RemotingHelper.DEFAULT_CHARSET)  
            );
            msg.setKeys("hello"+i);//要唯一
            //msg.setDelayTimeLevel(3);//延迟收到消息,官方示例上说3对应的是10秒,防止消费端过快处理
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);//同步发
            
            String msgId=sendResult.getMsgId();
            System.out.printf(new Date()+"发出去的返回的结果 %s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
