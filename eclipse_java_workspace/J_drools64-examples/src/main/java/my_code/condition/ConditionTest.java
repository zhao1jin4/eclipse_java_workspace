package my_code.condition;


import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

 

public class ConditionTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-condition");
        	
        	
        	/*********************and element start*********************/
        	Customer customer = new Customer(); 
	        customer.setCity("����");
	        
        	Account accout = new Account();
	        accout.setName("����������");
	        
	        kSession.insert(customer);
	        kSession.insert(accout);
	        /*********************and element end*********************/
        	
	        
	        
	        /*********************or element start*********************/
//        	Customer customer = new Customer(); 
//	        customer.setCity("�Ϻ�");
//	        
//        	Account accout = new Account();
//	        accout.setName("�ַ�����");
//	        
//	        kSession.insert(customer);
//	        kSession.insert(accout);
	        /*********************or element end*********************/
	        
	        
	        
	        /*********************exists element start*********************/
//        	Customer customer_exists = new Customer();
//        	customer_exists.setCity("�Ϻ�");
//        	kSession.insert(customer_exists);
	        /*********************exists element end*********************/
	        
	        
        	/*********************not element start*********************/
//        	Account account = new Account();
//        	account.setName("xx");
//        	kSession.insert(account);
        	/*********************not element end*********************/
        	
        	
        	
	        
            /*********************from element start*********************/
//        	Account accout1 = new Account();
//            Account accout2 = new Account();
//            Account accout3 = new Account();
//            accout1.setName("a1");
//            accout2.setName("a2");
//            accout3.setName("a2");
//            
//            Customer customer_from = new Customer();
//            customer_from.getAccounts().add(accout1);
//            customer_from.getAccounts().add(accout2);
//            customer_from.getAccounts().add(accout3);
//            
//            kSession.insert(customer_from);
        	/*********************from element end*********************/
            
            
            
            
        	
        	
            /*********************collect element start*********************/
//        	Account accout1 = new Account();
//            Account accout2 = new Account();
//            Account accout3 = new Account();
//            Account accout4 = new Account();
//            accout1.setStatus("Y");
//            accout2.setStatus("Y");
//            accout3.setStatus("Y");
//            accout4.setStatus("N");
//            
//           
//            kSession.insert(accout1);
//            kSession.insert(accout2);
//            kSession.insert(accout3);
//            kSession.insert(accout4);
//            
//            Customer customer_collect = new Customer();
//            kSession.insert(customer_collect);
        	/*********************collect element end*********************/
            
            
            
            
        	
        	
            /*********************accumulate element start*********************/
            /*average min max count sum*/
//            Account accout1 = new Account();
//            Account accout2 = new Account();
//            Account accout3 = new Account();
//            Account accout4 = new Account();
//            accout1.setNum(100);
//            accout2.setNum(100);
//            accout3.setNum(100);
//            accout4.setNum(200);
//            kSession.insert(accout1);
//            kSession.insert(accout2);
//            kSession.insert(accout3);
//            kSession.insert(accout4);
        	/*********************accumulate element end*********************/
            
            
            
            
            
            /*********************&&/|| Լ������ start*********************/
//            Customer customer = new Customer();
//            customer.setName("����");
//            customer.setAge(21);
//            customer.setCity("����");
//            customer.setGender("male");
//            kSession.insert(customer);
        	/*********************&&/|| Լ������ end*********************/
            
        	
            
            
            /*********************Contains Լ�������� start*********************/
//        	Account accout_contains1 = new Account();
//            Account accout_contains2 = new Account();
//            Account accout_contains3 = new Account();
//            Account accout_contains4 = new Account();
//            
//            Customer customer_contains = new Customer();
//            customer_contains.getAccounts().add(accout_contains1);
//            customer_contains.getAccounts().add(accout_contains2);
//            customer_contains.getAccounts().add(accout_contains3);
//            customer_contains.getAccounts().add(accout_contains4);
//            customer_contains.setName("����");
//            
//            kSession.insert(customer_contains);
//            kSession.insert(accout_contains2);
//            kSession.insert(accout_contains4);
        	/*********************Contains Լ�������� end*********************/
            
            
            
            
            
            /*********************not contains Լ�������� start*********************/
//        	Account accout_contains1 = new Account();
//            Account accout_contains2 = new Account();
//            Account accout_contains3 = new Account();
//            
//            Customer customer_contains = new Customer();
//            customer_contains.getAccounts().add(accout_contains1);
//            customer_contains.getAccounts().add(accout_contains2);
//            customer_contains.setName("����");
//            
//            kSession.insert(customer_contains);
//            kSession.insert(accout_contains3);
        	/*********************not contains Լ�������� end*********************/
            
            
            
            
            
            /*********************memberOf Լ�������� start*********************/
//        	Account account = new Account();
//        	account.setName("�ַ�����");
//            
//            Customer customer = new Customer();
//            customer.setName("����");
//            customer.getAccounts().add(account);
//            
//            kSession.insert(account);
//            kSession.insert(customer);
        	/*********************memberOf Լ�������� end*********************/
            
            
            
            /*********************not memberOf Լ�������� start*********************/
//        	Account account = new Account();
//        	account.setName("����������");
//            
//            Customer customer = new Customer();
//            customer.setName("����");
//            
//            kSession.insert(account);
//            kSession.insert(customer);
        	/*********************not memberOf Լ�������� end*********************/
            
            
            
            
            
            /*********************matches Լ�������� start*********************/
//        	Customer customer = new Customer();
//        	customer.setName("matchesXXXXXXXXXXX");
//            kSession.insert(customer);
        	/*********************matches Լ�������� end*********************/
        	
        	
            
        	/*********************not matches Լ�������� start*********************/
//            Customer customer = new Customer();
//        	customer.setName("XXXXXXXXXXXmatches");
//            kSession.insert(customer);
        	/*********************not matches Լ�������� end*********************/
            
            
            
            
            /*********************eval �����ⲿ����  start*********************/
//            Customer customer = new Customer();
//            customer.setName("����");
//            kSession.insert(customer);
        	/*********************eval �����ⲿ����  end*********************/

            
            

            /*********************this �ؼ���  start*********************/
//        	Customer customer = new Customer();
//        	customer.setName("david_this");
//            kSession.insert(customer);
        	/*********************this �ؼ���  end*********************/
            
            
            
        	//�������й���
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
