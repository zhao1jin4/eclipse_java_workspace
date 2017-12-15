package springdata_redis.subscribe1;

import java.io.Serializable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDataRedisSubscribeMain1 
{  
//测试OK
    public static void main(String[] args) {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springdata_redis/subscribe1/spring_redis_subscribe.xml");  
        RedisDAOImpl redisDAO= context.getBean("redisDAO",RedisDAOImpl.class);
       
        
        String msg = "Hello, Redis!";
        redisDAO.sendMessage("chatroom", msg); //发布字符串消息
 
 
        TestBean bean = new TestBean();
        bean.setName("ZhenQin");
        bean.setAge(28);
       
        redisDAO.sendMessage("chatroom", bean); //发布一个普通的javabean消息
 
 
        Integer[] values = new Integer[]{21341,123123,12323};
        redisDAO.sendMessage("chatroom", values);  //发布一个数组消息
    }
    
	
}

class TestBean implements Serializable
{
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}