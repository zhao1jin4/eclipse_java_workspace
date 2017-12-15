package springdata_redis.subscribe1;

import java.io.Serializable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDataRedisSubscribeMain1 
{  
//����OK
    public static void main(String[] args) {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springdata_redis/subscribe1/spring_redis_subscribe.xml");  
        RedisDAOImpl redisDAO= context.getBean("redisDAO",RedisDAOImpl.class);
       
        
        String msg = "Hello, Redis!";
        redisDAO.sendMessage("chatroom", msg); //�����ַ�����Ϣ
 
 
        TestBean bean = new TestBean();
        bean.setName("ZhenQin");
        bean.setAge(28);
       
        redisDAO.sendMessage("chatroom", bean); //����һ����ͨ��javabean��Ϣ
 
 
        Integer[] values = new Integer[]{21341,123123,12323};
        redisDAO.sendMessage("chatroom", values);  //����һ��������Ϣ
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