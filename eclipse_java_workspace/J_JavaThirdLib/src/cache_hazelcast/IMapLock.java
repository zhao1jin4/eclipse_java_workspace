
package cache_hazelcast;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class IMapLock { 
	public static void main(String[] args) throws Exception {

		Config config = new Config();

		HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
		final CountDownLatch latch=new CountDownLatch(2);
	
		new Thread() {
			@Override
			public void run() {
				 IMap<String, String> mapLock = h.getMap("my-map-lock");
				 mapLock.putIfAbsent("record1","value1");//上一线程在锁中，这里阻塞,多个Java进程间也是一样的
				 mapLock.lock("record1");
				 System.out.println("locking");
				 mapLock.unlock("record1");
				 
				latch.countDown();
			}
		}.start();
	

//		new Thread() {
//			@Override
//			public void run() {
//				 IMap<String, String> mapLock = h.getMap("my-map-lock");
//				 mapLock.putIfAbsent("record1","value1");
//				 mapLock.lock("record1");
//				 System.out.println("locking");
//				 mapLock.unlock("record1");
//				 
//				latch.countDown();
//			}
//		}.start();
		
	
		latch.await();
		h.shutdown();

	}

}
