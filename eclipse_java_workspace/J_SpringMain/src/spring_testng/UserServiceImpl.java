package spring_testng;

import javax.annotation.Resource;

public class UserServiceImpl  
{
	@Resource(name="userDao") //��xml����id="userDAO",�������������,ע��
	private UserDAO userDAO;
	
	public UserDAO getDao() {
		return userDAO;
	}
	public void setDao(UserDAO dao) {
		this.userDAO = dao;
	}
	 
}
