package hadoop.kafka;

import java.util.Arrays;
import java.util.Properties;
 
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MyKafkaConsumer {
	public static void main(String[] args)
	{
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "localhost:9092");
	     props.put("group.id", "group1");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test", "my-replicated-topic"));
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);//timeout millsecond ��ָ��ʱ��û�����ݾͷ��ؿռ���,�Զ��ύ,���ﷵ�ص���Ϣ����ȫ������ɹ�,����Ͷ���
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	     }
	     
	}
}