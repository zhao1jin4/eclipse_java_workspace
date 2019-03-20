package spring_testng;

import javax.annotation.Resource;

public class UserServiceImpl  
{
	@Resource(name="userDao") //在xml中找id="userDAO",并赋下面的属性,注入
	private UserDAO userDAO;
	
	public UserDAO getDao() {
		return userDAO;
	}
	public void setDao(UserDAO dao) {
		this.userDAO = dao;
	}
	 
}
