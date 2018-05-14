package redis_jedis;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

public class TestJedisShade  {
	

	ShardedJedisPool shardedPool;
	String ip="192.168.72.128";
	int port=6379;
	
	@Before
	public void init()
	{
		//通过一致性哈希算法决定把数据存到哪台上,算是一种客户端负载均衡
		 JedisPoolConfig config=new JedisPoolConfig();
		 config.setMaxIdle(20);
		 config.setMaxTotal(30);
		 config.setMaxWaitMillis(5*1000);
		 config.setTestOnBorrow(false);
		 
		JedisShardInfo shardInfo =new JedisShardInfo(ip,port);//passwd
		shardedPool=new ShardedJedisPool(config,Arrays.asList(shardInfo)) ;
		
	
	}
	@Test
	public void putKey()
	{
	
		ShardedJedis shardedJedis=shardedPool.getResource();
		shardedJedis.set("user1", "用户1");
		shardedJedis.expire("user1",20*60);//单位秒，20分
		//setnx,incr
		shardedJedis.set("user2", "用户2");
		
		 ShardedJedisPipeline pipeline = shardedJedis.pipelined();  
          pipeline.set("trainNo1_SH", "20");  
          pipeline.set("trainNo1_SZ", "10");  
         List<Object> res = pipeline.syncAndReturnAll();
         System.out.println(res);//[OK, OK]
         shardedJedis.close();//一定要close
	}
	@Test
	public void delKey()
	{
		String delKeyLike="user";
		ShardedJedis shardedJedis=shardedPool.getResource();
		Collection<Jedis> jedisCollect = shardedJedis.getAllShards();  
		Iterator<Jedis> iter = jedisCollect.iterator();  
		while (iter.hasNext()) 
		{  
		  Jedis jedis = iter.next();  
		  Set<String> keys = jedis.keys(delKeyLike + "*");  
		  jedis.del(keys.toArray(new String[keys.size()]));  
		}   
		 shardedJedis.close();//一定要close
	}
	
 
}
