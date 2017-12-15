package my_code;

import java.util.Map;

import com.google.common.collect.Maps;

public class Message {
	   public static final int HELLO = 0;
       public static final int GOODBYE = 1;

       private String message;

       private int status;

       public String getMessage() {
           return this.message;
       }

       public void setMessage(String message) {
           this.message = message;
       }

       public int getStatus() {
           return this.status;
       }

       public void setStatus(int status) {
           this.status = status;
       }

   	Map<String,Object> vars = Maps.newHashMap();
   	
   	public void set(String name, Object value){
   			vars.put(name.toUpperCase(),value);
   	}
   	public Object get(String name){
   		if(vars.containsKey(name.toUpperCase())){
   			return vars.get(name.toUpperCase());
   		}
   		return "";
   	}
   	
}
