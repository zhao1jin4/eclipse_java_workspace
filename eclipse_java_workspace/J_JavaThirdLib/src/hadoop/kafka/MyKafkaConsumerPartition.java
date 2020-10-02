package hadoop.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class MyKafkaConsumerPartition {
	public static void main(String[] args) {
		 boolean running=true;
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "localhost:9092");
	     props.put("group.id", "group1");
	     props.put("enable.auto.commit", "false");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     try {
	         while(running) {
	        	 ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(Long.MAX_VALUE)); 
//	             ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
	             for (TopicPartition partition : records.partitions()) { //∞¥partitionÃ·Ωªoffset
	                 List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
	                 for (ConsumerRecord<String, String> record : partitionRecords) {
	                     System.out.println(record.offset() + ": " + record.value());
	                 }
	                 long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
	                 consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
	             }
	         }
	     } finally {
	       consumer.close();
	     }
	}
	
	
	/*
	 // Shutdown hook which can be called from a separate thread
     public void shutdown() {
         closed.set(true);
         consumer.wakeup();
     }
	 */
}