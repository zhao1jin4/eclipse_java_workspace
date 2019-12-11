package hadoop.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

public class MyKafkaConsumerManualOffset {
	public static void main(String[] args) {
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "localhost:9092");
	     props.put("group.id", "group1");
	     props.put("enable.auto.commit", "false");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test", "my-replicated-topic"));
	     /*
	     //offset可存数据库中
	     consumer.subscribe(Arrays.asList("test", "my-replicated-topic"),new ConsumerRebalanceListener() {
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
			}
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
			}
	     });
	     */
	     final int minBatchSize = 20;
	     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
	     while (true) {
//	         ConsumerRecords<String, String> records = consumer.poll(100);
	         ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); 
	         for (ConsumerRecord<String, String> record : records) {
	             buffer.add(record);
	         }
	         if (buffer.size() >= minBatchSize) {
	        	 System.out.print("saveDB");
	             consumer.commitSync();//enable.auto.commit:false,如未处理完就断了,重启不会丢失
				  //但如果saveDB处理多条,部分成功,就down机就会重复消费,做幂等,比丢了好
	             //如DB commit成功，还没来的急Kafka commit就挂了，下次就会有重复数据
	             //方式二 异步
	             /*
	              consumer.commitAsync(new OffsetCommitCallback() {
					@Override
					public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {

					}} );
	             */
	             //当有新的消费者加入或才退出进，分区重新分配
	             buffer.clear();
	         }
	     }
	}
}
