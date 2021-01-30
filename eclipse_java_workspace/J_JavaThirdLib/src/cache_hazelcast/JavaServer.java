package cache_hazelcast;

import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
public class JavaServer {

	public static void main(String[] args) {
		
		Config config = new  Config();
		config.setClusterName("myHazelCluster");
		
		NetworkConfig network=config.getNetworkConfig();
		System.out.println(network.getPort());//Ĭ��5701
		network.setPort(6701);//�޸ļ����˿ڣ��类ռ��+1��ֱ������
		
		HazelcastInstance server = Hazelcast.newHazelcastInstance(config);
		
		IMap<Integer, String> mapCustomers = server.getMap("MyMap"); //creates the map proxy
		mapCustomers.put(1, "one");
		//���������ݺ�management-center�����UI http://127.0.0.1:8080 ����Storage->Maps���� MyMap-> Map Browser ��ť->������Ϊ1������ѡ��integer, �鿴ֵ
		IQueue<String> clusterQueue=server.getQueue("MyQueue");
		
		clusterQueue.add("element1");
		System.out.println(clusterQueue.size());
		System.out.println(clusterQueue.poll());
		System.out.println(clusterQueue.size());
		
	}

}
