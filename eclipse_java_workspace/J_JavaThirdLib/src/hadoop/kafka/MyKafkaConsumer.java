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
	    
	     //认值 latest （ 取最新的值），还有 earliest 可选 
	     //只有没有初始化（换组的时候）或没有数据了（过了7天的数据删了）才会生效
	     String doc=ConsumerConfig.AUTO_OFFSET_RESET_DOC; //是文档
	     props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	     
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test", "my-replicated-topic"));//可多个topic
	     while (true) {
	         //ConsumerRecords<String, String> records = consumer.poll(100);//timeout millsecond 
	    	 //如指定时间没有数据就返回空集合,自动提交,这里返回的消息必须全部处理成功,否则就丢了
	    	 ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); 
	         for (ConsumerRecord<String, String> record : records)
	         {
	        	 //record.topic();
	        	 System.out.printf("得到数据offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	         }
	         System.out.println("sleep 3 秒");
	         Thread.sleep(1000);
	     }
	     //consumer.close();
	}
}