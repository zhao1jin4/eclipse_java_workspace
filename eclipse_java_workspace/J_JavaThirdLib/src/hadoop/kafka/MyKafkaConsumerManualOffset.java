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
	     //offset�ɴ����ݿ���
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
	             consumer.commitSync();//enable.auto.commit:false,��δ������Ͷ���,�������ᶪʧ
				  //�����saveDB�������,���ֳɹ�,��down���ͻ��ظ�����,���ݵ�,�ȶ��˺�
	             //��DB commit�ɹ�����û���ļ�Kafka commit�͹��ˣ��´ξͻ����ظ�����
	             //��ʽ�� �첽
	             /*
	              consumer.commitAsync(new OffsetCommitCallback() {
					@Override
					public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {

					}} );
	             */
	             //�����µ������߼������˳������������·���
	             buffer.clear();
	         }
	     }
	}
}
