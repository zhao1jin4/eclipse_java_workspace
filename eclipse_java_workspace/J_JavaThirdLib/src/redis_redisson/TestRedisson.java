package redis_redisson;

import java.net.URI;
import java.util.Iterator;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RExecutorService;
import org.redisson.api.RKeys;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RQueue;
import org.redisson.api.RReliableTopic;
import org.redisson.api.RSet;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.StatusListener;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;

import io.netty.resolver.dns.DnsServerAddressStreamProvider; 

public class TestRedisson 
{
//	String ipPort="redis://127.0.0.1:6379";
	String ipPort="redis://192.168.42.129:6379";
	String masterIPPort="redis://192.168.1.59:7001";
	String slaveIPPort="redis://192.168.1.59:7004";
	@Test
	public void testShowAll() throws Exception
	{
		try 
		{
		 
			com.fasterxml.jackson.dataformat.yaml.YAMLFactory a;
			io.netty.resolver.dns.DnsServerAddressStreamProviders b;
			//jboss-client.jar 的 io.netty有影响 
			
			//--单机 
			Config config = new Config();
			SingleServerConfig singConfig= config.useSingleServer();
			singConfig.setDatabase(0);
			singConfig.setAddress("redis://127.0.0.1:6379"); 
			 //singConfig.setPassword("");
			 RedissonClient redsson = Redisson.create(config);
			 //二选一
//			 RedissonClient redsson =Redisson.create();
			 RKeys keys=redsson.getKeys();
			 Iterable<String> iter=keys.getKeys();
			 iter.forEach(new Consumer<String>()  //回调的要等才行
			 {
				@Override
				public void accept(String key) {
					System.out.println("key="+key);  
					
				}
			});
			 System.out.println("waiting...");
			 Thread.sleep(60*60*5);
			 redsson.shutdown();
		 
		 
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	@Test
	public void testPut() throws Exception
	{
		 //redisson 依赖于netty,fasterxml的jackson
		com.fasterxml.jackson.databind.jsontype.TypeSerializer x;
				
		Config config = new Config();
		//--单机 
		//SingleServerConfig singConfig= config.useSingleServer();
		//singConfig.setAddress(ipPort);//ip:port
		
		//--cluster配置
		MasterSlaveServersConfig  msConfig=config.useMasterSlaveServers();
		msConfig.setMasterAddress(masterIPPort);
		msConfig.addSlaveAddress(slaveIPPort);//可传多个node
		
		//Redisson redisson = Redisson.create();//默认  redis://127.0.0.1:6379
		RedissonClient redisson = Redisson.create(config);
	    
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
	
		    
		    lock.lock(10, TimeUnit.SECONDS); // acquire lock and automatically unlock it after 10 seconds
		    //...
		    lock.unlock();
		    
		    
		    long waitTime=3;
		    long releaseTime=10;
		    lock.tryLock(waitTime, releaseTime, TimeUnit.SECONDS);
			 // or wait for lock aquisition up to 100 seconds 
			 // and automatically unlock it after 10 seconds
			 boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
			 if (res) {
			    try {
			    	 //...
			    } finally {
			        lock.unlock();
			    }
			 } 
	    }
	    
	    
	    //--Distributed AtomicLong example
	    {
	    	
		    RAtomicLong atomicLong = redisson.getAtomicLong("anyAtomicLong");
		    atomicLong.set(3);
		    atomicLong.incrementAndGet();
		    atomicLong.get();
	    }
	    
	    RExecutorService executor =  redisson.getExecutorService("myExecutorService");
	    
	    //--Distributed CountDownLatch example
	    {
		    RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
		    latch.trySetCount(1);
		    latch.await();//会等...
	    }
	    {
		    // in other thread or other JVM
		    RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
		    latch.countDown();
	    }	    
	     
	    
	    //--Distributed Topic example
	    {
		    RTopic  topic = redisson.getTopic("anyTopic");
		    topic.addListener(new StatusListener() {
				@Override
				public void onUnsubscribe(String arg0) {
				}
				@Override
				public void onSubscribe(String arg0) {
				}
			});
	    }
	    {
	    	// in other thread or other JVM

		    RTopic topic = redisson.getTopic("anyTopic");
		    long clientsReceivedMessage = topic.publish(new SomeObject());
	    }
	 
	    
	    redisson.shutdown();
	}
	
	@Test // org/jboss/marshalling/ClassResolver  
	public void testTopic()  throws Exception{
		//--单机 
		Config config = new Config();
		SingleServerConfig singConfig= config.useSingleServer();
		singConfig.setAddress(ipPort);//ip:port
		singConfig.setDatabase(0);
		//singConfig.setPassword("");
		RedissonClient redisson = Redisson.create(config);

		RReliableTopic topic = redisson.getReliableTopic("anyTopic");
		topic.addListener(SomeObject.class, new MessageListener<SomeObject>() {
		    @Override
		    public void onMessage(CharSequence channel, SomeObject message) {
		       System.out.println("收到message"+message);
		    }
		});
		//会打印很多DEBUG日志
		
		// in other thread or JVM
		RReliableTopic topic1 = redisson.getReliableTopic("anyTopic");
		long receivedNums = topic1.publish(new SomeObject()); 
		 System.out.println("receivedNums="+receivedNums);
		//会调用xadd ，即要新版本redis
		
		
	}
}



