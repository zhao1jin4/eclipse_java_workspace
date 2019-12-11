package hadoop.kafka;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

//自定义分区,对于参数不传分区的情况，可参考kafka中已经实现的
public class CustomPartition implements Partitioner{
	@Override
	public void configure(Map<String, ?> configs) {
	}

	@Override
	public void close() {
	}	
	@Override
	public int 	partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster)
	{	
		System.out.println("到0号分区");
		return 0;
	}
}
