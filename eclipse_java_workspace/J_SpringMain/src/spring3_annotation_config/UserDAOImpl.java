package spring3_annotation_config;

public class UserDAOImpl implements UserDAO 
{
	private String test="123";//标识对象是否被修改了
	public void changeTest(String newStr)
	{
		test=newStr;
	}
	public String getTest() {
		return test;
	}
	public void save(String username) {
		System.out.println("a user saved!");
	}
	public String queryNameById(String id)
	{
		if("li".equals(id))
		{
			return "lisi";
		}else if ("wang".equals(id))
		{
			return "wangwu";
		}else
			return "unkown";
	}

	

}
