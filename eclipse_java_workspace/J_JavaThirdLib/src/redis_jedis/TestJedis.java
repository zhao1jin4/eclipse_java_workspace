package redis_jedis;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class TestJedis  {
	
	Jedis jedis;
	String ip="127.0.0.1";
	int port=6379;
	
	@Before
	public void init()
	{
		jedis=new Jedis(ip,port);//�˿�Ĭ�� 6379  , ���� �� cluster��master
		//jedis.auth("123456");//������ masterauth ,requirepass ��ֵ
	}
	@After
	public void destroy()
	{
		System.out.println("junit after");
		jedis.close();
	}
	
	/*
	private String parseByte(byte[] bytes)
	{
		ObjectInputStream oin=null;
		try {
			oin = new ObjectInputStream(new ByteArrayInputStream(bytes));//���У�������
			Object obj=oin.readObject();
			if(obj instanceof String)
				return obj.toString();
			String res=ToStringBuilder.reflectionToString(obj);
			return res;
		} catch ( Exception e) {
			e.printStackTrace(); 
		}finally {
			IOUtils.closeQuietly(oin); 
		}
		return null;
	}
	@Test
	public void testShowAllKeyValuesWithSpringSession() 
	{
		Set<String> keys=jedis.keys("*");
		for(String key:keys)
		{
			
			String type=jedis.type(key);//set,zset,list,hash
			System.out.println("type:"+type+",key="+key);
			if("zset".equals(type))
			{
				Set<byte[]> zsets=jedis.zrange(key.getBytes(), 0, -1); 
				System.out.println("\t  value== \t ");
				for(byte[] zset:zsets)
					System.out.println(" \t value="+parseByte(zset)+",score="+ jedis.zcard(zset));
			}else if ("set".equals(type))
			{
				Set<byte[]> values=jedis.smembers(key.getBytes()); 
				System.out.println(" \t value== \t ");
				for(byte[] value:values)
					System.out.println(" \t value="+parseByte(value));
			}else if("list".equals(type))
			{
				List<byte[]> values=jedis.lrange(key.getBytes(), 0, -1); 
				System.out.println("\t value== \t ");
				for(byte[] value:values)
					System.out.println(" \t value="+parseByte(value));
			}
			else if("hash".equals(type))
			{
				Set<byte[] > hkeys=jedis.hkeys(key.getBytes()); 
				System.out.println(" \t value== \t ");
				for(byte[]  hkey:hkeys)
					System.out.println(" \t \t "+hkey+"="+parseByte(jedis.hget(key.getBytes(), hkey)));
			}else if("string".equals(type))
			{
				String value=jedis.get(key); 
				System.out.println("\t value ="+value);
			}else
			{
				System.out.println("\t value =...." );
			}
		}

//		JedisCommands
	}
	*/
	
	@Test
	public void testShowAllKeyValuesString() 
	{
		Set<String> keys=jedis.keys("*");
		for(String key:keys)
		{
			
			String type=jedis.type(key);//set,zset,list,hash
			System.out.println("type:"+type+",key="+key);
			if("zset".equals(type))
			{
				Set<String> zsets=jedis.zrange(key, 0, -1); 
				System.out.println("\t  value== \t ");
				for(String zset:zsets)
					System.out.println(" \t value="+zset+",score="+ jedis.zcard(zset));
			}else if ("set".equals(type))
			{
				Set<String> values=jedis.smembers(key ); 
				System.out.println(" \t value== \t ");
				for(String value:values)
					System.out.println(" \t value="+value);
			}else if("list".equals(type))
			{
				List<String> values=jedis.lrange(key , 0, -1); 
				System.out.println("\t value== \t ");
				for(String value:values)
					System.out.println(" \t value="+value);
			}
			else if("hash".equals(type))
			{
				Set<String > hkeys=jedis.hkeys(key); 
				System.out.println(" \t value== \t ");
				for(String hkey:hkeys)
					System.out.println(" \t \t "+hkey+"="+ jedis.hget(key , hkey) );
			}else if("string".equals(type))
			{
				String value=jedis.get(key); 
				System.out.println("\t value ="+value);
			}else
			{
				System.out.println("\t value =...." );
			}
		}

//		JedisCommands
	}
	@Test
	public void testFlulshDB() 
	{
		jedis.flushDB();
	}
	
	//(��������) ���˲���ȷ��IP ��  redis.clients.jedis.exceptions.JedisMovedDataException: MOVED 5798 127.0.0.1:7001 ,ԭ�������Ҫʹ��cluster��Ӧ��client API
	@Test
	public void testPutSingleNode()//���ڵ����OK
	{
		String keys = "name";  
		if(jedis.exists(keys))
			jedis.del(keys);  
		jedis.set(keys, "Li ��");  
		System.out.println(jedis.get(keys));  
		 
	}
	
	
	@Test
	public void testPoolSingleNode()//���ڵ�,����OK
	{
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxIdle(20);
		config.setMaxTotal(30);
		config.setMaxWaitMillis(5*1000);
		config.setTestOnBorrow(false);
		config.setBlockWhenExhausted(false);//���Ӻľ�ʱ�Ƿ�����, false���쳣,ture����ֱ����ʱ, Ĭ��true
		//������ӵ���С����ʱ�� Ĭ��
		config.setMinEvictableIdleTimeMillis(20*60*1000);//20��
		
		
		JedisPool pool = new JedisPool(config, ip,port,2000);//timeout,�ɼ�passworld����
		
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");//string
		String foobar = jedis.get("foo");
		
		//zset
		jedis.zadd("sose", 0, "car");//0��score
		jedis.zadd("sose", 0, "bike"); 
		Set<String> sose = jedis.zrange("sose", 0, -1);//score ��Χ
		System.out.println(sose);
		
		jedis.sadd("myset","mysetval");//set
		jedis.lpush("mylist", "one");//list
		jedis.lpush("mylist", "two");
		jedis.hset("myhashStuScore", "zhang", "A");
		jedis.hset("myhashStuScore", "lisi", "B");
		jedis.close();//һ��Ҫclose
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
	 //�ֲ�ʽ��  ,redis����û�и�����
	@Test
	public void testRedisLuaDistributeLock()
	{
		//�ٷ�˵lua�ű���ԭ���� Atomicity of scripts����ű�����ִ�У����������ű�����ִ��
		//lua�ű�ʵ�ֲַ�ʽ�������Ա���֤setnx,expire����������ԭ���� 
		System.out.println("begin testRedisDistributeLock");
		//InputStream input=TestJedis.class.getResourceAsStream("/redis_jedis/checkandset.lua");
		InputStream input=TestJedis.class.getResourceAsStream("/redis_jedis/lock.lua");
		
		StringBuilder strBuilder=new StringBuilder();
		 Scanner scanner =new Scanner(input);
		 while(scanner.hasNextLine())
		 {
		 	String line=scanner.nextLine();
		 	strBuilder.append(line).append("\n");
		 }
		//checkandset.lua ��ֵΪ10 lua ����true,��ӡ��1����lua����false��ӡnull,���ٷ��ĵ�������ƥ��
		//Object res=jedis.eval(strBuilder.toString(),1,"key","10","20"); 
		Object res=jedis.eval(strBuilder.toString(),1,"lockExport","user1","30");//���� ����,ֵ����ʱ��
		System.out.println(res);
	}
	@Test
	public void testClusterPut()
	{
		// ./redis-trib.rb create --replicas 1 172.16.35.35:7000 172.16.35.35:7001 172.16.35.35:7002 172.16.35.35:7003 172.16.35.35:7004 172.16.35.35:7005
		//redis-trib.rb����IP��bind����IP
		String node0Ip="172.16.35.35"; int node0Port=7000;
		String node1Ip="172.16.35.35"; int node1Port=7001;
		String node2Ip="172.16.35.35"; int node2Port=7002;
		String node3Ip="172.16.35.35"; int node3Port=7003;
		String node4Ip="172.16.35.35"; int node4Port=7004;
		String node5Ip="172.16.35.35"; int node5Port=7005;
		
		
		//��Ⱥ��redis,������ commons/poopl2
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort(node0Ip, node0Port));
		jedisClusterNodes.add(new HostAndPort(node1Ip, node1Port));
		jedisClusterNodes.add(new HostAndPort(node2Ip, node2Port));
		jedisClusterNodes.add(new HostAndPort(node3Ip, node3Port));
		jedisClusterNodes.add(new HostAndPort(node4Ip, node4Port));
		jedisClusterNodes.add(new HostAndPort(node5Ip, node5Port)); 
		
		//ֻһ��master�ڵ�,������slaveû������ ,�������ѯʱ���Զ���ת��ָ���ڵ�,����ڵ�ɷ�ֹһ������
		JedisCluster jc = new JedisCluster(jedisClusterNodes);  
		
		//jc.auth("123456");
		//jc.set("jedisKey", "jeids��Ⱥ");  
		System.out.println(jc.get("jedisKey"));
		System.out.println(jc.get("clusterKey"));
		System.out.println(jc.get("foo"));
	}
}
