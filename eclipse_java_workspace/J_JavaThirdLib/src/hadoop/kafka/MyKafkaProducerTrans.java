package hadoop.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

public class MyKafkaProducerTrans {
	public static void main(String[] args) {
		 Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("transactional.id", "my-transactional-id");
		 //事务是对发送者发到topic多个分区中,可能在写一个分区时生产者挂了
		 //事务ID应该下次启动能恢复 (事务ID写死有没有影响？？，提交事务后，再次执行使用以前事务ID是否可以？？？？)
		 //事务ID和会话ID 保存到broker中 如生产者挂了再启进根据事务ID在broker中找到会话ID，就可以实现幂等
		  
		 Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
		 producer.initTransactions();

		 try {
		     producer.beginTransaction();
		     for (int i = 0; i < 100; i++)
		         producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
		     producer.commitTransaction();
		 } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
		     // We can't recover from these exceptions, so our only option is to close the producer and exit.
		     producer.close();
		 } catch (KafkaException e) {
		     // For all other exceptions, just abort the transaction and try again.
		     producer.abortTransaction();
		 }
		 producer.close();
	}
}