package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.example.demo.vo.UserVO;

import redis.clients.jedis.JedisCluster;

@RestController
public class DemoResetController {
	
	/*@Autowired
	private com.example.demo.dao.UserMapper userMapper;
	
	@RequestMapping("mybatis")  
	public List mybatis() {
 
 		org.apache.ibatis.session.SqlSession x;
		List<UserVO> list= userMapper.getAll();
		
		return list;
	}

	
	@Autowired
	private JedisCluster jedisCluster;
	 
	@RequestMapping("redisCluster")
	public String redisCluster() 
	 {
		 jedisCluster.set("clusterKey","集群值");
		return jedisCluster.get("clusterKey");
	}
 	*/
 
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
	
	@Cacheable("keyList") //Redis ,返回Bean 一定要Serializable
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
