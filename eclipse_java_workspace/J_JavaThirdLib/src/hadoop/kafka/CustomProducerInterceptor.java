package hadoop.kafka;

import java.util.Date;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

//�Զ���������
public class CustomProducerInterceptor implements ProducerInterceptor{
	@Override
	public void configure(Map<String, ?> configs) {
	}
	@Override
	public void close() {
	}
	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		if(metadata!=null)
		{
			System.out.println("ʱ��"+new Date()+",topic:"+metadata.topic()+" �յ�ȷ��");
		}else
		{
			exception.printStackTrace();
		}
	}
	@Override
	public ProducerRecord onSend(ProducerRecord record) {
		//��send����û��callback����ʱ���������
		System.out.println("ʱ��"+new Date()+",topic:"+record.topic()+"�Ѿ�����");//record.value()
		return record;
	}
}
