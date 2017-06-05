package springdata_redis.subscribe1;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MessageDelegateListenerImpl {

	public void onMessage(Object message) 
	{
		  //ʲô������,ֻ���
        if(message == null){
            System.out.println("null");
        } else if(message.getClass().isArray()){
            System.out.println(Arrays.toString((Object[])message));
        }else if(message instanceof List<?>) {
            System.out.println(message);
        } else if(message instanceof Map<? , ?>) {
            System.out.println(message);
        } else {
            System.out.println(ToStringBuilder.reflectionToString(message));
        }
	}
}