package spring_cache_map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public class AccountService {
	@Cacheable(value = "accountCache",condition="#userName.length() <= 4") // 使用了一个缓存名叫 accountCache
	public Account getAccountByName(String userName) {
		System.out.println("real query account." + userName);
		 return new Account( userName); 
	}
 
	 @CacheEvict(value="accountCache",key="#account.getName()")
	 // Key 是SpEL 表达式，这里因为我们保存的时候用的是 account 对象的 name 字段
	 public void updateAccount(Account account) {
		 System.out.println("updateAccount");
	 } 
	
	 //既要保证方法被调用，又希望结果被缓存
	@CachePut(value="accountCache",key="#account.getName()") 
	public Account updateAccount2(Account account) {  
		 System.out.println("updateAccount @CachePut ");
		 return new Account(account.getName()); 
	} 
	
	@CacheEvict(value = "accountCache", allEntries = true // 清空 accountCache 缓存
			,beforeInvocation=false) //beforeInvocation，缺省为 false 期间如果执行方法出现异常，则会导致缓存清空不被执行 
	public void reload() {
		System.out.println("清空 accountCache 缓存");
	}
	
	@Cacheable(value="accountCache",key="#userName.concat(#password)") 
	public Account getAccount(String userName,String password,boolean sendLog) {  
		System.out.println("getAccount multi param");
		return new Account( userName+password); 
	}
	

	public Account getAccountByNameInvalid(String userName) {
		System.out.println("getAccountByNameInvalid." + userName);
		 return getAccountByName( userName); //相同的类里，相当于this ,这里不走AOP
	}
	 
}