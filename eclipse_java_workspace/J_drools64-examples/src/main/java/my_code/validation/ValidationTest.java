package my_code.validation;


import java.math.BigDecimal;
import java.util.Date;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

 


public class ValidationTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-validation");
        	
        	
        	//addressRequired
            Customer customer_addressRequired = new Customer();
            customer_addressRequired.setDateOfBirth(new Date());
            customer_addressRequired.setFirstName("x");
            customer_addressRequired.setLastName("j");
            customer_addressRequired.setPhoneNumber("123456");
            kSession.insert(customer_addressRequired);
            
        	
            //phoneNumberRequired
//            Customer customer_phoneNumberRequired = new Customer();
//            customer_phoneNumberRequired.setAddress("shenzhen");
//            customer_phoneNumberRequired.setDateOfBirth(new Date());
//            customer_phoneNumberRequired.setFirstName("x");
//            customer_phoneNumberRequired.setLastName("j");
//            customer_phoneNumberRequired.setPhoneNumber(null);
//            kSession.insert(customer_phoneNumberRequired);
            
        	
            //accountOwnerRequired
//            Account account_accountOwnerRequired = new Account();
//            account_accountOwnerRequired.setBalance(new BigDecimal(200));
//            account_accountOwnerRequired.setOwner(null);
//            account_accountOwnerRequired.setUuid("abc");
//            kSession.insert(account_accountOwnerRequired);
        	
        	
        	//studentAccountCustomerAgeLessThan
//	        Customer customer = new Customer();
//	        customer.setAddress("shenzhen");
//	        customer.setDateOfBirth(new Date());
//	        customer.setFirstName("x");
//	        customer.setLastName("j");
//	        customer.setPhoneNumber("123456");
//	        kSession.insert(customer);
//	        Account account = new Account();
//	        account.setBalance(new BigDecimal(200));
//	        account.setOwner(new Customer());
//	        account.setUuid("abc");
//	        account.setType(Type.STUDENT);
//	        kSession.insert(account);
        	
        	
            //accountBalanceAtLeast
//            Account account_accountBalanceAtLeast = new Account();
//            account_accountBalanceAtLeast.setBalance(new BigDecimal(20));
//            account_accountBalanceAtLeast.setOwner(new Customer());
//            account_accountBalanceAtLeast.setUuid("abc");
//            kSession.insert(account_accountBalanceAtLeast);
            
            
            //accountNumberUnique
            Account account_accountNumberUnique = new Account();
            account_accountNumberUnique.setBalance(new BigDecimal(200));
            account_accountNumberUnique.setOwner(new Customer());
            account_accountNumberUnique.setUuid("1");
            kSession.insert(account_accountNumberUnique);
        	
            
        	//引爆所有规则
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
