package cache_hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class JavaClient {

	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setClusterName("dev");
		//��start.bat��������hazelcast����
		clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701", "127.0.0.1:5702");
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		IMap<Integer, String> mapCustomers = client.getMap("MyMap"); //creates the map proxy

		mapCustomers.put(1, "one");
		//���������ݺ�management-center�����UI http://127.0.0.1:8080 ����Storage->Maps���� MyMap-> Map Browser ��ť->������Ϊ1������ѡ��integer, �鿴ֵ
		IQueue<String> clusterQueue=client.getQueue("MyQueue");
		
		clusterQueue.add("element1");
		System.out.println(clusterQueue.size());
		System.out.println(clusterQueue.poll());
		System.out.println(clusterQueue.size());
		
	}

}
