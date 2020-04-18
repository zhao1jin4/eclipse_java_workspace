package redis_single;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisSingleController {
	@Autowired  
	private RedisTemplate<String,String> redisTemplate;  
 
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
	
	@Cacheable("cacheList") //Redis ,返回Bean 一定要Serializable
	@RequestMapping("cachePage")
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
	//测试OK
	@RequestMapping("redisTemplate")
	public String useRedisTemplate() {
		ValueOperations<String, String> ops=redisTemplate.opsForValue();
		ops.set("myKey", "my中文 ");
		return ops.get("myKey");
	}
}
