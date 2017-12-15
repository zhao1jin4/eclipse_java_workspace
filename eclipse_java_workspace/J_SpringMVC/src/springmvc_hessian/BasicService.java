package springmvc_hessian;


public class BasicService   implements BasicAPI 
{
	//不要重写doGet,doPost,只要实现自定义接口就OK,Servlet是单例的,这里只是Demo
	
  private String _greeting = "Hello, world";
  private MyRequest request;
  
  public void setGreeting(String greeting)
  {
	  _greeting = greeting;
  }
  public void setObject(  MyRequest req)
  {
	  this.request = req;
  }
  public MyRequest getObject()
  {
	  request.setSystemId("systemUniqueCode_" + request.getSystemId() );
	  return request;
  }
  public String hello()
  {
	  return _greeting;
  }
}