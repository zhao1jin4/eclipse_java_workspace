package springdata_redis;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;


public class SpringDataJedisTestMain {  

    public static void main(String[] args)
    {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:springdata_redis/spring_jedis.xml");
        RedisTemplate<String,Object>  redisTemplate = context.getBean("redisTemplate",RedisTemplate.class);  
        //其中key采取了StringRedisSerializer
        //其中value采取JdkSerializationRedisSerializer
        ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();  
        
        User u1 = new User("zhangsan",12);  
        User u2 = new User("lisi",25);  
        valueOper.set("u:u1", u1);  
        valueOper.set("u:u2", u2);  
        System.out.println(((User)valueOper.get("u:u1")).getName());  
        System.out.println(((User)valueOper.get("u:u2")).getName());  
        
        valueOper.set("key", 10);

        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("springdata_redis/checkandset.lua")));
        redisScript.setResultType(Boolean.class);
        
        boolean res= redisTemplate.execute(redisScript, Collections.singletonList("key"), 10, 20);//List keys,Object... args
        System.out.println(res);

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(context.getBean("redisConnectionFactory",RedisConnectionFactory.class)  );
        stringRedisTemplate.setEnableTransactionSupport(true);
        
        Object val= stringRedisTemplate.execute(new RedisCallback<Object>() 
		{
			public Object doInRedis(RedisConnection connection) throws DataAccessException 
			{
				Long size = connection.dbSize();
				//  如果使用StringRedisTemplate  则可以强转  StringRedisConnection
				((StringRedisConnection)connection).set("key", "value");
				return size;
			}
		});
       System.out.println(val);
 
        
        HashOperations<String,String,Object> hash=  redisTemplate.opsForHash();
        SetOperations<String,Object> set=  redisTemplate.opsForSet();
        ZSetOperations<String,Object> zset=  redisTemplate.opsForZSet();
        ListOperations<String,Object> list=redisTemplate.opsForList();
        
        String key="";
        
        BoundValueOperations<String,Object> boundValue=redisTemplate.boundValueOps(key);
        BoundHashOperations<String,String,Object> boundHash=redisTemplate.boundHashOps(key);
        BoundListOperations<String,Object> boundList=redisTemplate.boundListOps(key);
        BoundZSetOperations<String,Object> boundZset=redisTemplate.boundZSetOps(key);
        BoundSetOperations<String,Object> boundSet=redisTemplate.boundSetOps(key);
        
        context.close();
    }  
      
    static class User implements Serializable
    {  
        private static final long serialVersionUID = 1L;  
        private String name;  
        private Date created;  
        private int age;  
        public User(){}  
        public User(String name,int age){  
            this.name = name;  
            this.age = age;  
            this.created = new Date();  
        }  
        public String getName() {  
            return name;  
        }  
        public void setName(String name) {  
            this.name = name;  
        }  
        public Date getCreated() {  
            return created;  
        }  
        public void setCreated(Date created) {  
            this.created = created;  
        }  
        public int getAge() {  
            return age;  
        }  
        public void setAge(int age) {  
            this.age = age;  
        }  
          
    }  

}  