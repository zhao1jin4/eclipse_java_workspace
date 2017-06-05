package springdata_redis.subscribe1;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisDAOImpl {
 
    private RedisTemplate<String, Object> redisTemplate = null;
 
    public RedisDAOImpl() {
 
    }
 
    public void sendMessage(String channel, Serializable message) {
        redisTemplate.convertAndSend(channel, message);//listener��onMessage�ͻᱻ����
    }
 
 
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
 
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}