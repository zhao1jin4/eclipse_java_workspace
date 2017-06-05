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
        	
        	
            /********************* salience �������ȼ�  start*********************/
        	/********************* salience �������ȼ� end*********************/
            
        	/********************* no-loop �Ƿ�ѭ��  start*********************/
//        	Message message = new Message();
//        	kSession.insert(message);  
        	/********************* no-loop �Ƿ�ѭ�� end*********************/
        	
        	
        	/********************* date-effective ϵͳʱ��>= date-effective����  start*********************/
        	/********************* date-effective ϵͳʱ��>= date-effective����  end*********************/

            
        	
        	/********************* date-expires ϵͳʱ��<= date-expires����  start*********************/
        	/********************* date-expires ϵͳʱ��<= date-expires����  end*********************/
        	
        	
        	
        	
        	/********************* enabled ���ù����Ƿ���� start*********************/
        	/********************* enabled ���ù����Ƿ���� end*********************/
        	
        	
        
        	
            
            /********************* activation-group ֻ��һ���ᱻִ�� start*********************/
            /********************* activation-group ֻ��һ���ᱻִ�� end*********************/
        	
        	
        	/********************* agenda-group �����  ��Ҫ�õ�focus����ִ��   start*********************/
        	kSession.getAgenda().getAgendaGroup("agenda-group1").setFocus();
        	kSession.getAgenda().getAgendaGroup("agenda-group2").setFocus();
            /********************* agenda-group �����   ��Ҫ�õ�focus����ִ��    end*********************/
        	
        	
        	/********************* lock-on-active ��ǿ���no-loop����Ҫʹ����ruleflow-group��agenda-group���Ե�ʱ�� start*********************/
//        	Message message = new Message();
//        	kSession.getAgenda().getAgendaGroup("agenda-group").setFocus();
//        	kSession.insert(message); 
        	/********************* lock-on-active ��ǿ���no-loop����Ҫʹ����ruleflow-group��agenda-group���Ե�ʱ�� end*********************/
        	
        	
        	/********************* dialect mvel���� ֧�����Ժͷ�����ֱ�ӷ���  start*********************/
        	Message message = new Message();
        	message.setMessage("Hello World");
        	kSession.insert(message); 
        	/********************* dialect mvel���� ֧�����Ժͷ�����ֱ�ӷ���  end*********************/
        	
        	//�������й���
            kSession.fireAllRules();
            
        	/********************* duration ָ��ֵ֮�����һ���̴߳��� start*********************/
            System.out.println("Class Thread Id=" + Thread.currentThread().getId());
            Thread.sleep(5000);//duration ��������????
        	/********************* duration ָ��ֵ֮�����һ���̴߳���  end*********************/
         
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
