package redisson_springboot;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
  

@RestController
public class RedissonController { 
	@Autowired
	private RedissonClient  redissonClient;
	@Autowired
	private RedisTemplate   redisTemplate;
	
	@Autowired
	private ReactiveRedisTemplate   reactiveRedisTemplate;
	
	  
	@RequestMapping("redissonLock")
	public void redissonLock() throws Exception 
	 {
		System.out.println("调用 了redisson");
		RLock lock = redissonClient.getLock("anyLock");
		boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
		if (res) {
		   try {
			   System.out.println("lock doing ...");
		   } finally {
		       lock.unlock();
		   }
		} 
	}
	@RequestMapping("saveMap")
	public void redissonMap() throws Exception 
	{
		RMap<String, String> map = redissonClient.getMap("myMap");
		map.put("user1", "abc");
		map.put("user2", "def"); 
	}
	@RequestMapping("getMap")
	public String getMap() throws Exception 
	{
		RMap<String, String> map = redissonClient.getMap("myMap");
		return map.get("user1");
	}
	@RequestMapping("redisTemplate")
	public String redisTemplate() 
	{
		System.out.println("调用 了redisTemplate");
		ValueOperations<String, String> ops=	redisTemplate.opsForValue();
		ops.set("myKey", "my中文 ");
		return ops.get("myKey");
	}
	
	 
}
