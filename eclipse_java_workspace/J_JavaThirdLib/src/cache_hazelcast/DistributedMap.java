
package cache_hazelcast;

import java.util.concurrent.ConcurrentMap;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class DistributedMap {
/*
 ���� ���  redis

Hazelcast IMDG ��Դ�� in-memory data grid
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast</artifactId>
    <version>4.0.1</version>
</dependency>  �������� jar����С10MB


 */
	public static void main(String[] args) {

		Config config = new Config();

		HazelcastInstance h = Hazelcast.newHazelcastInstance(config);

		ConcurrentMap<String, String> map = h.getMap("my-distributed-map");

		map.put("key", "value");

		// Concurrent Map methods

		map.putIfAbsent("somekey", "somevalue");

		map.replace("key", "value", "newvalue");

		map.forEach((k, v) -> System.out.println(k + " => " + v));

		h.shutdown();

	}

}
