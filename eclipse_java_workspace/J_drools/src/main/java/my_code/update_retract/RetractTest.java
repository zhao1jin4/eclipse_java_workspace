package my_code.update_retract;


import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import my_code.Message;


public class RetractTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-retract");
        	
        	
            /*********************modify/insert/update/retract/kcontext  start*********************/
        	Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            kSession.insert(message);
        	/*********************modify/insert/update/retract/kcontext  end*********************/
            
            
            
        	//引爆所有规则
            kSession.fireAllRules();
            
//            System.out.println(message.getMessage());
//            System.out.println(message.getStatus());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
