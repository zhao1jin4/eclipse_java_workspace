package cache_hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.collection.IQueue;
import com.hazelcast.collection.ItemEvent;
import com.hazelcast.collection.ItemListener;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.MapEvent;
import com.hazelcast.map.impl.MapListenerAdapter;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryEvictedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapClearedListener;
import com.hazelcast.map.listener.MapEvictedListener; 
public class JavaClient {

	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setClusterName("dev");
		//先start.bat启动两个hazelcast服务
		clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701", "127.0.0.1:5702");
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		IMap<Integer, String> mapCustomers = client.getMap("MyMap"); //creates the map proxy
		
		//客户端缓存变化事件
		mapCustomers.addEntryListener(new MapListenerAdapter<String,String> () {
			@Override
			public void entryAdded(EntryEvent<String, String> event) {
				System.out.println(event);
			}
			@Override
			public void entryEvicted(EntryEvent<String, String> event) {
				System.out.println(event);
			}
			@Override
			public void entryRemoved(EntryEvent<String, String> event) {
				System.out.println(event);
			}
			@Override
			public void mapCleared(MapEvent event) {
				System.out.println(event);
			}
			@Override
			public void mapEvicted(MapEvent event) {
				System.out.println(event);
			}
		},  true);

		mapCustomers.put(1, "one");
		//当存了数据后，management-center服务后UI http://127.0.0.1:8080 可以Storage->Maps进入 MyMap-> Map Browser 按钮->键输入为1，类型选择integer, 查看值
		IQueue<String> clusterQueue=client.getQueue("MyQueue");
		
		
		//客户端缓存变化事件
		clusterQueue.addItemListener(new ItemListener<String>() {
			@Override
			public void itemRemoved(ItemEvent<String> event) {
				System.out.println(event);
			}
			@Override
			public void itemAdded(ItemEvent<String> event) {
				System.out.println(event);
			}
		}, true);
		
		clusterQueue.add("element1");
		System.out.println(clusterQueue.size());
		System.out.println(clusterQueue.poll());
		System.out.println(clusterQueue.size());
	}
}

//EntryXxxListener类名同redisson名字
class MyListener implements EntryAddedListener<String,String>,EntryRemovedListener<String,String>,EntryUpdatedListener<String,String>
							, EntryEvictedListener<String,String>,MapEvictedListener,MapClearedListener
{
	@Override
	public void entryEvicted(EntryEvent<String, String> event) {
		System.out.println(event);
	}
	@Override
	public void entryUpdated(EntryEvent<String, String> event) {
		System.out.println(event);
	}
	@Override
	public void entryRemoved(EntryEvent<String, String> event) {
		System.out.println(event);
	}
	@Override
	public void entryAdded(EntryEvent<String, String> event) {
		System.out.println(event);
	}
	@Override
	public void mapCleared(MapEvent event) {
		System.out.println(event);
	}
	@Override
	public void mapEvicted(MapEvent event) {
		System.out.println(event);
	}
}


