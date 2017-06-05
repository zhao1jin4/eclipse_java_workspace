package hadoop.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class MySendMessage {
	public static void main(String[] args) {
		Properties props=new Properties();
		props.put("metadata.broker.list", "localhost:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "hadoop.kafka.SimplePartitioner");
		props.put("request.required.acks", "1");
		 
		ProducerConfig conf=new ProducerConfig(props);
		Producer<String, String> producer=new Producer<String, String> (conf);
		
		Random rnd = new Random();
		long runtime = new Date().getTime();
		String ip = "192.168.2." + rnd.nextInt(255);
		String msg = runtime + ",www.example.com," + ip;
		
		KeyedMessage<String, String> data = new KeyedMessage<String, String>("my-replicated-topic", ip, msg);
		producer.send(data);
		
		 producer.close();
		
	}
	
}
