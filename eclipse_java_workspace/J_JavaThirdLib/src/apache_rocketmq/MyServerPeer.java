package apache_rocketmq;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class MyServerPeer {

	/*
<dependency>
   <groupId>org.apache.rocketmq</groupId>
   <artifactId>rocketmq-common</artifactId>
   <version>4.2.0</version>
</dependency>
<dependency>
   <groupId>org.apache.rocketmq</groupId>
   <artifactId>rocketmq-client</artifactId>
   <version>4.2.0</version>
</dependency>
<dependency>
   <groupId>org.apache.rocketmq</groupId>
   <artifactId>rocketmq-remoting</artifactId>
   <version>4.2.0</version> 
</dependency>


https://github.com/apache/rocketmq-externals/tree/master/rocketmq-console
mvn spring-boot:run
����
mvn clean package -Dmaven.test.skip=true
java -jar target/rocketmq-console-ng-1.0.0.jar  --server.port=8888 --rocketmq.config.namesrvAddr=127.0.01:9876;192.168.1.107:9876

	 */
	public static void main(String[] args) throws Exception {

		String mqGroup = "myGroup";
		String mqIP = "127.0.0.1:9876";
		String mqTopic = "myTopic";
		String mqTag = "myTag";
		
		String appName="app-1";
		
		//����/���� ��
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(mqGroup);
		
		consumer.setConsumerGroup(mqGroup);
		consumer.setNamesrvAddr(mqIP);
		consumer.setVipChannelEnabled(false);
		consumer.setConsumeThreadMax(10);//������Ϣ�����10���̴߳�����ֹ�̹߳��ർ�����ݿ������ù�
		consumer.setConsumeThreadMin(5);
		//���ù㲥����  ���� MessageModel.CLUSTERING
//        consumer.setMessageModel(MessageModel.BROADCASTING);//Ҫ��Ӧ��  MessageListenerConcurrently
//        consumer.setInstanceName(appName);//�����BROADCASTING�ĵ�һ��app����ȫ��down����������ʱ�ͻᶪʧǰ�����Ϣ
        //��һ��app������2̨�����յ���ͬ����Ϣ(��㲻̫�ã����շ���Ҫ���ݵȣ�JMS �־û�topic������������)
        
		 //��������,ÿ����ȡ10��
//        consumer.setConsumeMessageBatchMaxSize(10);
        
		//����ǵ�һ����������ô�����ϴ����ѵ�λ�ü�������
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//		CONSUME_FROM_LAST_OFFSET //Ĭ�ϲ��ԣ��Ӹö�����β��ʼ���ѣ���������ʷ��Ϣ   
//		CONSUME_FROM_FIRST_OFFSET //�Ӷ����ʼ��ʼ���ѣ�����ʷ��Ϣ����������broker�ģ�ȫ������һ��
//		CONSUME_FROM_TIMESTAMP//��ĳ��ʱ��㿪ʼ���ѣ���setConsumeTimestamp()���ʹ�ã�Ĭ���ǰ��Сʱ��ǰ
		
		//������CONSUME_FROM_LAST_OFFSET����ʷ����Ϣ���Ǳ������ˡ��� ԭ�������ֻ��ȫ�µ�������Ż�ʹ�õ���Щ���ԣ��ϵ������鶼�ǰ��Ѿ��洢�������ѽ��ȼ������ѡ�
		
//		consumer.subscribe(mqTopic, mqTag);
		consumer.subscribe(mqTopic, "myTag || TagB || TagC || TagD || TagE"); //ƥ������ || ��   * ,�紫nullͬ*
		
		 
		MessageListenerOrderly messageListenerOrderly= new MessageListenerOrderly() //��˳���
		{
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext)
            {
            	MessageExt msg=list.get(0);
            	if(msg.getTags().contains(mqTag))//�����ĸ�tag,���ڴ��Ͷ�����Ϣ��ʽ
            		  System.out.println("���tag��"+mqTag);
				System.out.println(new Date()+appName+"�����յ���Ϣ�壺"+new String(msg.getBody(),Charset.forName("UTF-8")));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        };
		
		MessageListenerConcurrently messageListenerConcurrently= new MessageListenerConcurrently() //BROADCASTING
		{
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) 
			{
				//Ҫ���ݵ�
				MessageExt msg=list.get(0);
            	if(msg.getTags().contains(mqTag))//�����ĸ�tag,���ڴ��Ͷ�����Ϣ��ʽ
            		  System.out.println("���tag��"+mqTag);
				System.out.println(new Date()+appName+"�����յ���Ϣ�壺"+new String(msg.getBody(),Charset.forName("UTF-8")));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // CONSUME_SUCCESS
			}
        };
        consumer.registerMessageListener(messageListenerOrderly); 
//		consumer.registerMessageListener(messageListenerConcurrently); //BROADCASTING
		
		consumer.start();
		System.out.println(new Date()+"���� "+appName+"����");
		
	}

}
