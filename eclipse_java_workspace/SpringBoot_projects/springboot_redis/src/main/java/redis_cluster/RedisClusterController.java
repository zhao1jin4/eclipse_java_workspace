package redis_cluster;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
  

import redis.clients.jedis.JedisCluster;

@RestController
public class RedisClusterController {
//	@Autowired
//	private RedisConnectionFactory connectionFactory;
//	
//	//@Cacheable("redisCluster") //Redis cluster不用这个
//	@RequestMapping("redisCluster")
//	public String redisCluster()
//	{
//		System.out.println("调用 了redisCluster");
//		RedisClusterConnection connection = connectionFactory.getClusterConnection();
//		connection.set("clusterKey".getBytes(),"集群值".getBytes()); 
//		 String res=new String(connection.get("clusterKey".getBytes()));
//		System.out.println("调用 了redisCluster res"+res);
//		return res;
//	}
	//----------
	@Autowired
	private JedisCluster jedisCluster;
	
	//video teach  测试OK
	//redis-trib.rb外网IP，bind外网IP
	//./redis-trib.rb create --replicas 1 172.16.35.35:7000 172.16.35.35:7001 172.16.35.35:7002 172.16.35.35:7003 172.16.35.35:7004 172.16.35.35:7005
	//@Cacheable("redisCluster") //Redis cluster不用这个
	@RequestMapping("redisCluster")
	public String redisCluster() 
	 {
		System.out.println("调用 了redisCluster");
		jedisCluster.set("clusterKey","SpringBoot集群值");
		return jedisCluster.get("clusterKey");
	}
   //--------------
//	@Autowired  
//	RedisTemplate<String, String> redisTemplate;  
//
//	@RequestMapping("redisCluster")
//	public String redisCluster() 
//	{
//		System.out.println("调用 了redisCluster");
//		
//		ClusterOperations<String,String>cluserterOps = redisTemplate.opsForCluster(); 
//		
//		ValueOperations<String,String> ops= redisTemplate.opsForValue();
//		ops.set("clusterKey","集群值");  
//	    String valueFromRedis = ops.get("clusterKey");  
//	    System.out.println("调用 了redisCluster res"+valueFromRedis);
//	    return valueFromRedis;
//	    
//	}
	
	
	
	
	@RequestMapping("sayHello")
	public String sayHello() {
		return "hello world";
	}
	
	@RequestMapping("pojo")
	public UserVO showUser() {
		UserVO user=new UserVO();
		user.setId(32);
		user.setUsername("李");
		user.setBirthday(new Date());
		return user;
	}
	
	@RequestMapping("map")
	public Map showMap() {
		Map<String,Object> map=new HashMap<>();
		UserVO user=new UserVO();
		user.setId(32);
		user.setUsername("李");
		user.setBirthday(new Date());
		
		map.put("user1", user);
		map.put("key2","value2");
		return map;
	}
	
	
	@RequestMapping("list")
	public List showList() {
		System.out.println("调用了showList方法 ");
		List<UserVO> list=new ArrayList<>();
		
		UserVO user=new UserVO();
		user.setId(32);
		user.setUsername("李");
		user.setBirthday(new Date());
		
		list.add( user); 
		
		
		
		UserVO user2=new UserVO();
		user2.setId(322);
		user2.setUsername("李2");
		user2.setBirthday(new Date());
		
		list.add( user2); 
		
		
		return list;
	} 
	
}
