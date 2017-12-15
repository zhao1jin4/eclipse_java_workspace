package redis_redisson;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.redisson.Config;
import org.redisson.MasterSlaveServersConfig;
import org.redisson.Redisson;
import org.redisson.SingleServerConfig;
import org.redisson.core.MessageListener;
import org.redisson.core.RAtomicLong;
import org.redisson.core.RBucket;
import org.redisson.core.RCountDownLatch;
import org.redisson.core.RList;
import org.redisson.core.RLock;
import org.redisson.core.RMap;
import org.redisson.core.RQueue;
import org.redisson.core.RSet;
import org.redisson.core.RTopic;

public class TestRedisson 
{
	@Test
	public void testPut() throws Exception
	{
		 //redisson 依赖于netty,fasterxml的jackson
		com.fasterxml.jackson.databind.jsontype.TypeSerializer x;
		
		String ipPort="192.168.1.59:6379";
		String masterIPPort="192.168.1.59:7001";
		String slaveIPPort="192.168.1.59:7004";
		
		Config config = new Config();
		//--单机 
		//SingleServerConfig singConfig= config.useSingleServer();
		//singConfig.setAddress(ipPort);//ip:port
		
		//--cluster配置
		MasterSlaveServersConfig  msConfig=config.useMasterSlaveConnection();
		msConfig.setMasterAddress(masterIPPort);
		msConfig.addSlaveAddress(slaveIPPort);//可传多个node
		
		//Redisson redisson = Redisson.create();//默认  127.0.0.1:6379
	    Redisson redisson = Redisson.create(config);
	    
	    {
	    	//---Distributed Object storage example
		    RBucket<AnyObject> bucket = redisson.getBucket("anyObject");
		    bucket.set(new AnyObject());//单机OK,但cluster master 卡住???
		   // bucket.setAsync(new AnyObject());//单机OK,但cluster master get时卡住???
		    AnyObject obj = bucket.get();
		    
		    System.out.println(obj.getId()+"_"+obj.getName());
	    }
	 
	    {
		    //---Distributed Map example
	
		    RMap<String, SomeObject> map = redisson.getMap("anyMap");
		    SomeObject prevObject = map.put("123", new SomeObject());
		    SomeObject currentObject = map.putIfAbsent("323", new SomeObject());
		    SomeObject obj = map.remove("123");
	
		    map.fastPut("321", new SomeObject());
		    map.fastRemove("321");
	
		    Future<SomeObject> putAsyncFuture = map.putAsync("321", new SomeObject() );
		    Future<Boolean> fastPutAsyncFuture = map.fastPutAsync("321", new SomeObject());
	
		    map.fastPutAsync("321", new SomeObject());
		    map.fastRemoveAsync("321");
	    }

	    //--Distributed Set  example
	    {
	    	RSet<SomeObject> set = redisson.getSet("anySet");
	    	set.add(new SomeObject());
	    	set.remove(new SomeObject());

	    	set.addAsync(new SomeObject());

	    }
	    //--Distributed List  example
	    {
			RList<SomeObject> list = redisson.getList("anyList");
			list.add(new SomeObject());
			list.get(0);
			list.remove(new SomeObject());
	    }
	    
	    //--Distributed Queue example
	    {

			RQueue<SomeObject> queue = redisson.getQueue("anyQueue");
			queue.add(new SomeObject());
			SomeObject obj = queue.peek();
			SomeObject someObj = queue.poll();

	    }
	    
	    //--Distributed Lock example
	    {
	    	
		    RLock lock = redisson.getLock("anyLock");
		    lock.lock();
		    //...
		    lock.unlock();
	
		    // Lock time-to-live support
		    // releases lock automatically after 10 seconds
		    // if unlock method not invoked
		    lock.lock(10, TimeUnit.SECONDS);
		    //...
		    lock.unlock();
	    }
	    
	    
	    //--Distributed AtomicLong example
	    {
	    	
		    RAtomicLong atomicLong = redisson.getAtomicLong("anyAtomicLong");
		    atomicLong.set(3);
		    atomicLong.incrementAndGet();
		    atomicLong.get();
	    }
	    //--Distributed CountDownLatch example
//	    {
//		    RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
//		    latch.trySetCount(1);
//		    latch.await();//会等...
//	    }
//	    {
//		    // in other thread or other JVM
//		    RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
//		    latch.countDown();
//	    }	    
	    
	    
	    
	    //--Distributed Topic example
	    {
		    RTopic<SomeObject> topic = redisson.getTopic("anyTopic");
		    topic.addListener(new MessageListener<SomeObject>() {
		         public void onMessage(SomeObject message) {
		           // ...
		         }
		    });
	    }
	    {
	    	// in other thread or other JVM

		    RTopic<SomeObject> topic = redisson.getTopic("anyTopic");
		    long clientsReceivedMessage = topic.publish(new SomeObject());
	    }
	 
	    
	    redisson.shutdown();
	}
}



