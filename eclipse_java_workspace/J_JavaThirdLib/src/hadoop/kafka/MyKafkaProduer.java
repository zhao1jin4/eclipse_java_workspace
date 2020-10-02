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
		//���첽���ͣ�����һ�����з��ؽ���ٷ���һ��
		Properties props = new Properties();
		//props.put("bootstrap.servers", "localhost:9094");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9094");
		props.put("acks", "all");//all ��leader,flow����ɹ� ������ack ,�ɱ����������ݣ����ڷ���ackǰ,leader���ˣ����������ظ�����
		props.put("retries", 0);
		props.put("batch.size", 16384);//�����С��д��buffer.memory
		props.put("linger.ms", 1);//�ȴ�ʱ�䣬��û�е�batch.size�Ĵ�С����ô��ʱ��ͷ���
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"hadoop.kafka.CustomPartition");//�Լ�����
		props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,"hadoop.kafka.CustomProducerInterceptor");//�Լ�����
		
		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 10; i++)
		{
			producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
			/*
			//���ص�  ProducerRecord��Ҳ�ɴ�����,�Ͳ���ʹ���Զ������
			Future<RecordMetadata> future=producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)),new Callback() {
				//����lamda���ʽ
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception==null)
					{
						System.out.println("������topic="+metadata.topic()+",parition="+metadata.partition()+",offset="+metadata.offset()+",ValueSize="+metadata.serializedValueSize());
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
		 //д��Ϣ�ɴ� partition id,�粻���ʹ�keyʹ��key��hash�����ĸ�partition id,��Ҳû��key�״���������������ĸ�partition�������1
	}
}