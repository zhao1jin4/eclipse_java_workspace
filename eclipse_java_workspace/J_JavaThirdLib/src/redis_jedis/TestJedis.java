package redis_jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class TestJedis  {
	
	Jedis jedis;
	String ip="192.168.227.128";
	int port=6379;
	
	@Before
	public void init()
	{
		jedis=new Jedis(ip,port);//端口默认 6379  , 单机 或 cluster的master
		//jedis.auth("123456");//对配置 masterauth ,requirepass 的值
	}
	//(加了密码) 连了不正确的IP 报  redis.clients.jedis.exceptions.JedisMovedDataException: MOVED 5798 127.0.0.1:7001 ,原因可能是要使用cluster对应的client API
	@Test
	public void testPutSingleNode()//单节点测试OK
	{
		String keys = "name";  
		if(jedis.exists(keys))
			jedis.del(keys);  
		jedis.set(keys, "Li召进");  
		System.out.println(jedis.get(keys));  
		 
	}
	
	
	@Test
	public void testPoolSingleNode()//单节点,测试OK
	{
		JedisPoolConfig config=new JedisPoolConfig();
//		config.setMaxIdle(maxIdle);
//		config.setMaxWaitMillis(maxWaitMillis);
		
		JedisPool pool = new JedisPool(config, ip,port,2000);//timeout,可加passworld参数
		
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		String foobar = jedis.get("foo");
		
		jedis.zadd("sose", 0, "car");//0是score
		jedis.zadd("sose", 0, "bike"); 
		Set<String> sose = jedis.zrange("sose", 0, -1);//score 范围
		System.out.println(sose);
		pool.returnResource(jedis); //将资源归还个连接池
		pool.destroy();
	}
	
	@Test
	public void testTransactionSingleNode() 
	{
		
		jedis.watch("name");// 当前客户端监视该name键
		//jedis.unwatch();    // 撤销监视
	    
	    Transaction tran = jedis.multi();	// 开启事务状态
	    
	    tran.set("name", "benson");	// 添加键值对
	    tran.set("job", "java");
	    Response<String> res= tran.get("job");
		//tran.discard();		// 取消上述命令的执行
	    List<Object> list = tran.exec();	// 提交事务
	    System.out.println( res.get());//get在exec后执行
	    
	    //一次性取全部,如是set命令结果为OK
	    for(Object resp : list) {
	      System.out.println(resp.getClass().getName()+resp);
	    }
	    
	    
	    Transaction t = jedis.multi();
	    t.set("fool", "bar"); 
	    Response<String> result1 = t.get("fool");

	    t.zadd("foo", 1, "barowitch"); t.zadd("foo", 0, "barinsky"); t.zadd("foo", 0, "barikoviev");//是0
	    Response<Set<String>> sose = t.zrange("foo", 1, -1); 
	    List<Object> allResults =t.exec();

	    String foolbar = result1.get();
	    Set<String> set=sose.get();//如有错误,清数据   dump.rdb ,0分的只有第一个
	}
	@Test
	public void testPipline()
	{
		Pipeline p = jedis.pipelined();
		p.set("fool", "bar"); 
		p.zadd("foo", 1, "barowitch");  p.zadd("foo", 0, "barinsky"); p.zadd("foo", 0, "barikoviev");
		Response<String> pipeString = p.get("fool"); // 先多次发送命令,过后再取结果,像transaction
		Response<Set<String>> sose = p.zrange("foo", 0, -1);
		p.sync(); 

		Set<String> set=sose.get();//如有错误,清数据
		int soseSize = set.size();
		Set<String> setBack = sose.get();
		System.out.println(setBack);//全部
		
	}
	
	@Test
	public void testListener()
	{
		MyListener listen = new MyListener();//extends JedisPubSub
		//jedis.psubscribe(listen, "a","b");//会一直阻塞,会根据参数个数n 调用n次JedisPubSub的onSubscribe方法
		jedis.subscribe(listen, "foo");//会一直阻塞,会调用JedisPubSub的onSubscribe方法
	}
	@Test
	public void testPublish()
	{
		jedis.publish("foo", "消息");//如已经有 subscribe(psubscribe无效)进程,则subscribe的进程会调用JedisPubSub的onMessage方法
	}
	
	@Test
	public void testClusterPut()
	{
		String node0Ip="192.168.227.128"; int node0Port=7000;
		String node1Ip="192.168.227.128"; int node1Port=7001;
		String node2Ip="192.168.227.128"; int node2Port=7002;
		String node3Ip="192.168.227.128"; int node3Port=7003;
		String node4Ip="192.168.227.128"; int node4Port=7004;
		String node5Ip="192.168.227.128"; int node5Port=7005;
		
		
		//集群的redis,依赖于 commons/poopl2
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort(node2Ip, node2Port));//只一个master节点OK
		JedisCluster jc = new JedisCluster(jedisClusterNodes);  
		
		//jc.auth("123456");
		jc.set("foo3", "bar");  
				 
		System.out.println(jc.get("foo"));
	}
}
