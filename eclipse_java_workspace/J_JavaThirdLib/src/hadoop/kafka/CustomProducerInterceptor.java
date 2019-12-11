package hadoop.kafka;

import java.util.Date;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

//自定义拦截器
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
			System.out.println("时间"+new Date()+",topic:"+metadata.topic()+" 收到确认");
		}else
		{
			exception.printStackTrace();
		}
	}
	@Override
	public ProducerRecord onSend(ProducerRecord record) {
		//当send方法没有callback参数时这个才有用
		System.out.println("时间"+new Date()+",topic:"+record.topic()+"已经发出");//record.value()
		return record;
	}
}
