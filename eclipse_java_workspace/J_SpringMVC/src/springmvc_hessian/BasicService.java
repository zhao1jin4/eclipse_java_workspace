package springmvc_hessian;


public class BasicService   implements BasicAPI 
{
	//��Ҫ��дdoGet,doPost,ֻҪʵ���Զ���ӿھ�OK,Servlet�ǵ�����,����ֻ��Demo
	
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