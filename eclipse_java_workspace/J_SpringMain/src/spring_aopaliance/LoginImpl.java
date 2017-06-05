package spring_aopaliance;
public class LoginImpl implements ILogin
{
	public void login(String user,String password)
	{
		if (user.equals("zhangsan")&& password.equals("123"))
			System.out.println("login succed");
		else if (user.equals("lisi"))
			throw new IllegalArgumentException("不可以的用户名");
		else System.out.println("login succed");
	}
}
