package memcache_client.spymemcached;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.spring.MemcachedClientFactoryBean;

public class MainSpymemcached {

	public static void main(String[] args) throws Exception 
	{
		MemcachedClient c=new MemcachedClient( AddrUtil.getAddresses("192.168.0.184:12000"));
		MemcachedClient mc = new MemcachedClient(new InetSocketAddress("192.168.0.184", 12000));  
		Future<Boolean> theSetFuture = mc.set("myKey1", 900, "someObject");//key,timeout,value
		
		if(theSetFuture.get().booleanValue()==true)
		{  
			Future<Object> theGetFuture = mc.asyncGet("myKey1");
			Object obj=theGetFuture.get();
			 
			Future<Boolean> f = mc.replace("myKey1", 500, "MyValue1");  
			
			Collection<String> keys=new ArrayList<>();
			keys.add("myKey1");
			Map<String, Object> myBuilks=mc.getBulk(keys);
			
			Future<Map<String, Object>> theFutureBulk = mc.asyncGetBulk(keys);  
			Map<String, Object>   map = theFutureBulk.get(3,TimeUnit.SECONDS);
			
			 //del
			 Future<Boolean> theDelFuture = mc.delete("myKey1");
			 if(theDelFuture.get().booleanValue()==true)
			 {
				 theGetFuture = mc.asyncGet("myKey1");
				 obj=theGetFuture.get();
			 }
		}
		mc.delete("myAtomicNum");
		Thread.sleep(200);
		//Future<Boolean> numFuture = mc.add("myAtomicNum", 500, 20);//add 如已存在,返回false
		long res=mc.incr("myAtomicNum",500,  1);//前先set不行的 
		Object num=mc.get("myAtomicNum");
		Future<Long> numAsyncFuture= mc.asyncIncr("myAtomicNum",10); 
		Thread.sleep(200);
		num=mc.get("myAtomicNum");
		
		mc.shutdown(); 
		//---为 Spring
		MemcachedClientFactoryBean factoryBean=new MemcachedClientFactoryBean();
		factoryBean.setServers("192.168.0.184:12000");
		factoryBean.setOpTimeout(1000);//操作超时时间是1秒
		factoryBean.setTimeoutExceptionThreshold(1998);//设置超时次数上限是1998次
		MemcachedClient client=(MemcachedClient)factoryBean.getObject();
		
	}

}
