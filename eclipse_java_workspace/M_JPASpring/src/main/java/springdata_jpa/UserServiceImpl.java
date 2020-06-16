package springdata_jpa;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserDao userDao;

	@Transactional 
	public Long createNewAccount(  String name, String pwd, Integer init)
	{ 
		// 封装域对象
		AccountInfo accountInfo = new AccountInfo(); 
		accountInfo.setBalance(30);
		accountInfo.setAccountId(123L);
		// 调用持久层，完成数据的保存
		return userDao.save(accountInfo); 
	}
}