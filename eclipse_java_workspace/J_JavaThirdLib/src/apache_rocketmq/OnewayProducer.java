package apache_rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class OnewayProducer {
    public static void main(String[] args) throws Exception{
    	String mqGroup = "myGroup";
		String mqIP = "localhost:9876";
		String mqTopic = "myTopic";
		String mqTag = "myTag";

		String key="123";
		String msgStr="hello –°¿Ó";
		
		
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(mqGroup);
        producer.setNamesrvAddr(mqIP);
        //Launch the instance.
        producer.start();
        
        
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(mqTopic, mqTag,
                (msgStr + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
