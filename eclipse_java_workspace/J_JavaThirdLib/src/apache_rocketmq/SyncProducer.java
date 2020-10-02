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

		String msgStr="hello С��";
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
            msg.setKeys("hello"+i);//ҪΨһ
            //msg.setDelayTimeLevel(3);//�ӳ��յ���Ϣ,�ٷ�ʾ����˵3��Ӧ����10��,��ֹ���Ѷ˹��촦��
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);//ͬ����
            
            String msgId=sendResult.getMsgId();
            System.out.printf(new Date()+"����ȥ�ķ��صĽ�� %s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
