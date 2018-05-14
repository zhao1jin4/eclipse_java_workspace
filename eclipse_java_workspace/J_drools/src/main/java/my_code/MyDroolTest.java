package my_code;


import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

 


public class MyDroolTest {

    public static   void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-my");
        	
        	
            /********************* salience 设置优先级  start*********************/
        	/********************* salience 设置优先级 end*********************/
            
        	/********************* no-loop 是否循环  start*********************/
//        	Message message = new Message();
//        	kSession.insert(message);  
        	/********************* no-loop 是否循环 end*********************/
        	
        	
        	/********************* date-effective 系统时间>= date-effective触发  start*********************/
        	/********************* date-effective 系统时间>= date-effective触发  end*********************/

            
        	
        	/********************* date-expires 系统时间<= date-expires触发  start*********************/
        	/********************* date-expires 系统时间<= date-expires触发  end*********************/
        	
        	
        	
        	
        	/********************* enabled 设置规则是否可用 start*********************/
        	/********************* enabled 设置规则是否可用 end*********************/
        	
        	
        
        	
            
            /********************* activation-group 只有一个会被执行 start*********************/
            /********************* activation-group 只有一个会被执行 end*********************/
        	
        	
        	/********************* agenda-group 议程组  需要得到focus才能执行   start*********************/
        	kSession.getAgenda().getAgendaGroup("agenda-group1").setFocus();
        	kSession.getAgenda().getAgendaGroup("agenda-group2").setFocus();
            /********************* agenda-group 议程组   需要得到focus才能执行    end*********************/
        	
        	
        	/********************* lock-on-active 增强版的no-loop，主要使用在ruleflow-group或agenda-group属性的时候 start*********************/
//        	Message message = new Message();
//        	kSession.getAgenda().getAgendaGroup("agenda-group").setFocus();
//        	kSession.insert(message); 
        	/********************* lock-on-active 增强版的no-loop，主要使用在ruleflow-group或agenda-group属性的时候 end*********************/
        	
        	
        	/********************* dialect mvel方言 支持属性和方法的直接访问  start*********************/
        	Message message = new Message();
        	message.setMessage("Hello World");
        	kSession.insert(message); 
        	/********************* dialect mvel方言 支持属性和方法的直接访问  end*********************/
        	
        	//引爆所有规则
            kSession.fireAllRules();
            
        	/********************* duration 指定值之后的另一个线程触发 start*********************/
            System.out.println("Class Thread Id=" + Thread.currentThread().getId());
            Thread.sleep(5000);//duration 不起作用????
        	/********************* duration 指定值之后的另一个线程触发  end*********************/
         
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
