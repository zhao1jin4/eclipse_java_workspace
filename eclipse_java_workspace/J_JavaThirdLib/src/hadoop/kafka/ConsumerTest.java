package hadoop.kafka;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchRequest;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

public class ConsumerTest {

	public static void main(String[] args) throws Exception {

		int partition = 0;
		String topic = "my-replicated-topic";
		String clientName = "Client_" + topic + "_" + partition;

		SimpleConsumer consumer = new SimpleConsumer("127.0.0.1", 9092, 100000, 64 * 1024, "leaderLookup");
		TopicMetadataResponse resp = consumer.send(new TopicMetadataRequest(Collections.singletonList(topic)));
		List<TopicMetadata> metaData = resp.topicsMetadata();
		PartitionMetadata returnMetaData=null;
		for (TopicMetadata item : metaData) {
			for (PartitionMetadata part : item.partitionsMetadata()) {
				if (part.partitionId() == partition) {
					returnMetaData = part;
				}
			}
		}
		if (returnMetaData==null || returnMetaData.leader() == null) {
			return;
		}
		String leadBroker = returnMetaData.leader().host();
		consumer.close();
		
		consumer = new SimpleConsumer(leadBroker, 9092, 100000, 64 * 1024,clientName);
		Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
		requestInfo.put( new TopicAndPartition(topic, partition), 
					new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.EarliestTime(), 1) );
		OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
		OffsetResponse response = consumer.getOffsetsBefore(request);
		long readOffset = response.offsets(topic, partition)[0];
		
		kafka.api.FetchRequest req = new FetchRequestBuilder().clientId(clientName)
				.addFetch(topic, partition, readOffset, 100000).build();
		FetchResponse fetchResponse = consumer.fetch(req);
		
		for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(topic, partition) ) 
		{
			messageAndOffset.nextOffset();
			ByteBuffer payload = messageAndOffset.message().payload();
			byte[] bytes = new byte[payload.limit()];
			payload.get(bytes);
			System.out.println(String.valueOf(messageAndOffset.offset()) + ": "+ new String(bytes, "UTF-8"));
		}

	}
}
