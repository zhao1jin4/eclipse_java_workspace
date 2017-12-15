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
		jedis=new Jedis(ip,port);//�˿�Ĭ�� 6379  , ���� �� cluster��master
		//jedis.auth("123456");//������ masterauth ,requirepass ��ֵ
	}
	//(��������) ���˲���ȷ��IP ��  redis.clients.jedis.exceptions.JedisMovedDataException: MOVED 5798 127.0.0.1:7001 ,ԭ�������Ҫʹ��cluster��Ӧ��client API
	@Test
	public void testPutSingleNode()//���ڵ����OK
	{
		String keys = "name";  
		if(jedis.exists(keys))
			jedis.del(keys);  
		jedis.set(keys, "Li�ٽ�");  
		System.out.println(jedis.get(keys));  
		 
	}
	
	
	@Test
	public void testPoolSingleNode()//���ڵ�,����OK
	{
		JedisPoolConfig config=new JedisPoolConfig();
//		config.setMaxIdle(maxIdle);
//		config.setMaxWaitMillis(maxWaitMillis);
		
		JedisPool pool = new JedisPool(config, ip,port,2000);//timeout,�ɼ�passworld����
		
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		String foobar = jedis.get("foo");
		
		jedis.zadd("sose", 0, "car");//0��score
		jedis.zadd("sose", 0, "bike"); 
		Set<String> sose = jedis.zrange("sose", 0, -1);//score ��Χ
		System.out.println(sose);
		pool.returnResource(jedis); //����Դ�黹�����ӳ�
		pool.destroy();
	}
	
	@Test
	public void testTransactionSingleNode() 
	{
		
		jedis.watch("name");// ��ǰ�ͻ��˼��Ӹ�name��
		//jedis.unwatch();    // ��������
	    
	    Transaction tran = jedis.multi();	// ��������״̬
	    
	    tran.set("name", "benson");	// ��Ӽ�ֵ��
	    tran.set("job", "java");
	    Response<String> res= tran.get("job");
		//tran.discard();		// ȡ�����������ִ��
	    List<Object> list = tran.exec();	// �ύ����
	    System.out.println( res.get());//get��exec��ִ��
	    
	    //һ����ȡȫ��,����set������ΪOK
	    for(Object resp : list) {
	      System.out.println(resp.getClass().getName()+resp);
	    }
	    
	    
	    Transaction t = jedis.multi();
	    t.set("fool", "bar"); 
	    Response<String> result1 = t.get("fool");

	    t.zadd("foo", 1, "barowitch"); t.zadd("foo", 0, "barinsky"); t.zadd("foo", 0, "barikoviev");//��0
	    Response<Set<String>> sose = t.zrange("foo", 1, -1); 
	    List<Object> allResults =t.exec();

	    String foolbar = result1.get();
	    Set<String> set=sose.get();//���д���,������   dump.rdb ,0�ֵ�ֻ�е�һ��
	}
	@Test
	public void testPipline()
	{
		Pipeline p = jedis.pipelined();
		p.set("fool", "bar"); 
		p.zadd("foo", 1, "barowitch");  p.zadd("foo", 0, "barinsky"); p.zadd("foo", 0, "barikoviev");
		Response<String> pipeString = p.get("fool"); // �ȶ�η�������,������ȡ���,��transaction
		Response<Set<String>> sose = p.zrange("foo", 0, -1);
		p.sync(); 

		Set<String> set=sose.get();//���д���,������
		int soseSize = set.size();
		Set<String> setBack = sose.get();
		System.out.println(setBack);//ȫ��
		
	}
	
	@Test
	public void testListener()
	{
		MyListener listen = new MyListener();//extends JedisPubSub
		//jedis.psubscribe(listen, "a","b");//��һֱ����,����ݲ�������n ����n��JedisPubSub��onSubscribe����
		jedis.subscribe(listen, "foo");//��һֱ����,�����JedisPubSub��onSubscribe����
	}
	@Test
	public void testPublish()
	{
		jedis.publish("foo", "��Ϣ");//���Ѿ��� subscribe(psubscribe��Ч)����,��subscribe�Ľ��̻����JedisPubSub��onMessage����
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
		
		
		//��Ⱥ��redis,������ commons/poopl2
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort(node2Ip, node2Port));//ֻһ��master�ڵ�OK
		JedisCluster jc = new JedisCluster(jedisClusterNodes);  
		
		//jc.auth("123456");
		jc.set("foo3", "bar");  
				 
		System.out.println(jc.get("foo"));
	}
}
