package hadoop.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MyKafkaConsumer {
	public static void main(String[] args) throws Exception
	{
		/*
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>2.3.1</version>
</dependency>
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-streams</artifactId>
    <version>2.3.1</version>
</dependency>
		 */
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "localhost:9092");
	     //props.put("group.id", "group1");
	     props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    
	     //��ֵ latest �� ȡ���µ�ֵ�������� earliest ��ѡ 
	     //ֻ��û�г�ʼ���������ʱ�򣩻�û�������ˣ�����7�������ɾ�ˣ��Ż���Ч
	     String doc=ConsumerConfig.AUTO_OFFSET_RESET_DOC; //���ĵ�
	     props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	     
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test", "my-replicated-topic"));//�ɶ��topic
	     while (true) {
	         //ConsumerRecords<String, String> records = consumer.poll(100);//timeout millsecond 
	    	 //��ָ��ʱ��û�����ݾͷ��ؿռ���,�Զ��ύ,���ﷵ�ص���Ϣ����ȫ������ɹ�,����Ͷ���
	    	 ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); 
	         for (ConsumerRecord<String, String> record : records)
	         {
	        	 //record.topic();
	        	 System.out.printf("�õ�����offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	         }
	         System.out.println("sleep 3 ��");
	         Thread.sleep(1000);
	     }
	     //consumer.close();
	}
}