
package cache_hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

public class DistributedTopic implements MessageListener<String> {

	static HazelcastInstance h;

	public static void main(String[] args) {

		Config config = new Config();

		h = Hazelcast.newHazelcastInstance(config);

		ITopic<String> topic = h.getTopic("my-distributed-topic");

		topic.addMessageListener(new DistributedTopic());

		topic.publish("Hello to distributed world");

	}

	@Override
	public void onMessage(Message<String> message) {
		System.out.println("Got message " + message.getMessageObject());

		h.shutdown();

	}

}
