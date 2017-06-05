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
	        customer.setCity("北京");
	        
        	Account accout = new Account();
	        accout.setName("哈尔滨银行");
	        
	        kSession.insert(customer);
	        kSession.insert(accout);
	        /*********************and element end*********************/
        	
	        
	        
	        /*********************or element start*********************/
//        	Customer customer = new Customer(); 
//	        customer.setCity("上海");
//	        
//        	Account accout = new Account();
//	        accout.setName("浦发银行");
//	        
//	        kSession.insert(customer);
//	        kSession.insert(accout);
	        /*********************or element end*********************/
	        
	        
	        
	        /*********************exists element start*********************/
//        	Customer customer_exists = new Customer();
//        	customer_exists.setCity("上海");
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
            
            
            
            
            
            /*********************&&/|| 约束连接 start*********************/
//            Customer customer = new Customer();
//            customer.setName("张三");
//            customer.setAge(21);
//            customer.setCity("深圳");
//            customer.setGender("male");
//            kSession.insert(customer);
        	/*********************&&/|| 约束连接 end*********************/
            
        	
            
            
            /*********************Contains 约束操作符 start*********************/
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
//            customer_contains.setName("张三");
//            
//            kSession.insert(customer_contains);
//            kSession.insert(accout_contains2);
//            kSession.insert(accout_contains4);
        	/*********************Contains 约束操作符 end*********************/
            
            
            
            
            
            /*********************not contains 约束操作符 start*********************/
//        	Account accout_contains1 = new Account();
//            Account accout_contains2 = new Account();
//            Account accout_contains3 = new Account();
//            
//            Customer customer_contains = new Customer();
//            customer_contains.getAccounts().add(accout_contains1);
//            customer_contains.getAccounts().add(accout_contains2);
//            customer_contains.setName("张三");
//            
//            kSession.insert(customer_contains);
//            kSession.insert(accout_contains3);
        	/*********************not contains 约束操作符 end*********************/
            
            
            
            
            
            /*********************memberOf 约束操作符 start*********************/
//        	Account account = new Account();
//        	account.setName("浦发银行");
//            
//            Customer customer = new Customer();
//            customer.setName("张三");
//            customer.getAccounts().add(account);
//            
//            kSession.insert(account);
//            kSession.insert(customer);
        	/*********************memberOf 约束操作符 end*********************/
            
            
            
            /*********************not memberOf 约束操作符 start*********************/
//        	Account account = new Account();
//        	account.setName("哈尔滨银行");
//            
//            Customer customer = new Customer();
//            customer.setName("李四");
//            
//            kSession.insert(account);
//            kSession.insert(customer);
        	/*********************not memberOf 约束操作符 end*********************/
            
            
            
            
            
            /*********************matches 约束操作符 start*********************/
//        	Customer customer = new Customer();
//        	customer.setName("matchesXXXXXXXXXXX");
//            kSession.insert(customer);
        	/*********************matches 约束操作符 end*********************/
        	
        	
            
        	/*********************not matches 约束操作符 start*********************/
//            Customer customer = new Customer();
//        	customer.setName("XXXXXXXXXXXmatches");
//            kSession.insert(customer);
        	/*********************not matches 约束操作符 end*********************/
            
            
            
            
            /*********************eval 调用外部方法  start*********************/
//            Customer customer = new Customer();
//            customer.setName("张三");
//            kSession.insert(customer);
        	/*********************eval 调用外部方法  end*********************/

            
            

            /*********************this 关键字  start*********************/
//        	Customer customer = new Customer();
//        	customer.setName("david_this");
//            kSession.insert(customer);
        	/*********************this 关键字  end*********************/
            
            
            
        	//引爆所有规则
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
