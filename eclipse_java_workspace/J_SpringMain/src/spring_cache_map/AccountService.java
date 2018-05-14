package spring_cache_map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public class AccountService {
	@Cacheable(value = "accountCache",condition="#userName.length() <= 4") // ʹ����һ���������� accountCache
	public Account getAccountByName(String userName) {
		System.out.println("real query account." + userName);
		 return new Account( userName); 
	}
 
	 @CacheEvict(value="accountCache",key="#account.getName()")
	 // Key ��SpEL ���ʽ��������Ϊ���Ǳ����ʱ���õ��� account ����� name �ֶ�
	 public void updateAccount(Account account) {
		 System.out.println("updateAccount");
	 } 
	
	 //��Ҫ��֤���������ã���ϣ�����������
	@CachePut(value="accountCache",key="#account.getName()") 
	public Account updateAccount2(Account account) {  
		 System.out.println("updateAccount @CachePut ");
		 return new Account(account.getName()); 
	} 
	
	@CacheEvict(value = "accountCache", allEntries = true // ��� accountCache ����
			,beforeInvocation=false) //beforeInvocation��ȱʡΪ false �ڼ����ִ�з��������쳣����ᵼ�»�����ղ���ִ�� 
	public void reload() {
		System.out.println("��� accountCache ����");
	}
	
	@Cacheable(value="accountCache",key="#userName.concat(#password)") 
	public Account getAccount(String userName,String password,boolean sendLog) {  
		System.out.println("getAccount multi param");
		return new Account( userName+password); 
	}
	

	public Account getAccountByNameInvalid(String userName) {
		System.out.println("getAccountByNameInvalid." + userName);
		 return getAccountByName( userName); //��ͬ������൱��this ,���ﲻ��AOP
	}
	 
}