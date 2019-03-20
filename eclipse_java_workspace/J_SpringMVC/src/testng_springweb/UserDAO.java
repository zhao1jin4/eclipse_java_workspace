package testng_springweb;

public interface UserDAO 
{
	public void changeTest(String newStr);
	public String getTest();
	
	public void save(String username);
	public String queryNameById(String id);
}
