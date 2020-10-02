package hadoop.kafka;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class MyKafkaProduer {
	public static void main(String[] args) {
		//是异步发送，不会一定等有返回结果再发下一个
		Properties props = new Properties();
		//props.put("bootstrap.servers", "localhost:9094");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9094");
		props.put("acks", "all");//all 等leader,flow保存成功 ，返回ack ,可保护不丢数据，如在返回ack前,leader挂了，但可能有重复数据
		props.put("retries", 0);
		props.put("batch.size", 16384);//到达大小就写到buffer.memory
		props.put("linger.ms", 1);//等待时间，如没有到batch.size的大小，这么长时间就发送
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"hadoop.kafka.CustomPartition");//自己的类
		props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,"hadoop.kafka.CustomProducerInterceptor");//自己的类
		
		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 10; i++)
		{
			producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
			/*
			//带回调  ProducerRecord类也可传分区,就不会使用自定义分区
			Future<RecordMetadata> future=producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)),new Callback() {
				//可用lamda表达式
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception==null)
					{
						System.out.println("发送了topic="+metadata.topic()+",parition="+metadata.partition()+",offset="+metadata.offset()+",ValueSize="+metadata.serializedValueSize());
					}else
					{
						exception.printStackTrace();
					}
				}
			}); 
			*/
		}
		producer.close();
		 // Serializers/deserializers (serde) 
		 //写消息可传 partition id,如不传就传key使用key的hash决定哪个partition id,如也没传key首次生成随机数决定哪个partition，后面加1
	}
}