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
或者
mvn clean package -Dmaven.test.skip=true
java -jar target/rocketmq-console-ng-1.0.0.jar  --server.port=8888 --rocketmq.config.namesrvAddr=127.0.01:9876;192.168.1.107:9876

	 */
	public static void main(String[] args) throws Exception {

		String mqGroup = "myGroup";
		String mqIP = "127.0.0.1:9876";
		String mqTopic = "myTopic";
		String mqTag = "myTag";
		
		String appName="app-1";
		
		//服务/消费 端
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(mqGroup);
		
		consumer.setConsumerGroup(mqGroup);
		consumer.setNamesrvAddr(mqIP);
		consumer.setVipChannelEnabled(false);
		consumer.setConsumeThreadMax(10);//接收消息最多启10个线程处理，防止线程过多导致数据库连接用光
		consumer.setConsumeThreadMin(5);
		//设置广播消费  还有 MessageModel.CLUSTERING
//        consumer.setMessageModel(MessageModel.BROADCASTING);//要对应用  MessageListenerConcurrently
//        consumer.setInstanceName(appName);//如果是BROADCASTING的当一个app服务全部down机，再启动时就会丢失前面的消息
        //如一个app服务有2台都会收到相同的消息(这点不太好，接收方法要做幂等，JMS 持久化topic好像不是这样的)
        
		 //批量消费,每次拉取10条
//        consumer.setConsumeMessageBatchMaxSize(10);
        
		//如果非第一次启动，那么按照上次消费的位置继续消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//		CONSUME_FROM_LAST_OFFSET //默认策略，从该队列最尾开始消费，即跳过历史消息   
//		CONSUME_FROM_FIRST_OFFSET //从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
//		CONSUME_FROM_TIMESTAMP//从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
		
		//我设了CONSUME_FROM_LAST_OFFSET，历史的消息还是被消费了”？ 原因就在于只有全新的消费组才会使用到这些策略，老的消费组都是按已经存储过的消费进度继续消费。
		
//		consumer.subscribe(mqTopic, mqTag);
		consumer.subscribe(mqTopic, "myTag || TagB || TagC || TagD || TagE"); //匹配多个用 || 或   * ,如传null同*
		
		 
		MessageListenerOrderly messageListenerOrderly= new MessageListenerOrderly() //有顺序的
		{
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext)
            {
            	MessageExt msg=list.get(0);
            	if(msg.getTags().contains(mqTag))//区分哪个tag,用于传送多种消息格式
            		  System.out.println("这个tag是"+mqTag);
				System.out.println(new Date()+appName+"服务收到消息体："+new String(msg.getBody(),Charset.forName("UTF-8")));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        };
		
		MessageListenerConcurrently messageListenerConcurrently= new MessageListenerConcurrently() //BROADCASTING
		{
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) 
			{
				//要做幂等
				MessageExt msg=list.get(0);
            	if(msg.getTags().contains(mqTag))//区分哪个tag,用于传送多种消息格式
            		  System.out.println("这个tag是"+mqTag);
				System.out.println(new Date()+appName+"服务收到消息体："+new String(msg.getBody(),Charset.forName("UTF-8")));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // CONSUME_SUCCESS
			}
        };
        consumer.registerMessageListener(messageListenerOrderly); 
//		consumer.registerMessageListener(messageListenerConcurrently); //BROADCASTING
		
		consumer.start();
		System.out.println(new Date()+"服务 "+appName+"启动");
		
	}

}
