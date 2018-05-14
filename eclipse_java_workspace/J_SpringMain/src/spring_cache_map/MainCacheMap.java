package spring_cache_map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainCacheMap {

	public static void main(String[] args) {
		springCache();
		//myCache();
	}
	public static void springCache( ) {
		   ApplicationContext context = new ClassPathXmlApplicationContext( "/spring_cache_map/spring-cache-map.xml");
			    
		    AccountService s = (AccountService) context.getBean("accountServiceBean"); 
		    String key="some";
		    // ��һ�β�ѯ��Ӧ�������ݿ�
		    s.getAccountByName(key); 
		    
		    
		    // �ڶ��β�ѯ��Ӧ�ò������ݿ⣬ֱ�ӷ��ػ����ֵ
		    Account account1=s.getAccountByName(key); 
		    
		    
		    s.updateAccount(account1); 
		    account1=s.getAccountByName(key); 
		    
		    s.reload(); 
		    s.getAccountByName(key);
		    
		    
		    s.getAccount("lisi", "123", true);
		    s.getAccount("lisi", "123", true);
		    
		    account1 = s.updateAccount2(account1); 
		    s.getAccountByName(account1.getName());
		    
		    
		    s.getAccountByNameInvalid(account1.getName());
	}
	public static void myCache( ) 
	{
		  ApplicationContext context = new ClassPathXmlApplicationContext( "/spring_cache_map/spring-cache-my.xml");
		  AccountService s = (AccountService) context.getBean("accountServiceBean"); 


		  	String key="some";
		    // ��һ�β�ѯ��Ӧ�������ݿ�
		    s.getAccountByName(key); 
		    
		    
		    // �ڶ��β�ѯ��Ӧ�ò������ݿ⣬ֱ�ӷ��ػ����ֵ
		    Account account1=s.getAccountByName(key); 
		    
		    
		    s.updateAccount(account1); 
		    account1=s.getAccountByName(key); 
		    
		    s.reload(); 
		    s.getAccountByName(key);
		    
		    
		    s.getAccount("lisi", "123", true);
		    s.getAccount("lisi", "123", true);
		    
		    account1 = s.updateAccount2(account1); 
		    s.getAccountByName(account1.getName());
		    
	}
}
