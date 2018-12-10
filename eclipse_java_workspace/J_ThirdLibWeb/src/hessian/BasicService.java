package hessian;

import javax.servlet.annotation.WebServlet;

import com.caucho.hessian.server.HessianServlet;

//@WebServlet(urlPatterns={"/hessian/server"})
@WebServlet("/hessian/server")
public class BasicService extends HessianServlet implements BasicAPI 
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
	  System.out.println("服务端收到的BigDecimal金额是"+req.getAmt());
	  /*
Hessian 自动配置BigDecimal,要用BigDecimal.valueOf()不要用new BigDecimal()
hessian.jar/META-INF/hessian/serializers 
java.math.BigDecimal=com.caucho.hessian.io.BigDecimalDeserializer

hessian.jar/META-INF/hessian/deserializers
java.math.BigDecimal=com.caucho.hessian.io.StringValueSerializer
*/
	  
	  System.out.println("服务端收到的startDate是"+req.getStartDate());
	  
	  this.request = req;
  }
  public MyRequest getObject()
  {
	  request.setSystemId("systemUniqueCode_" + request.getSystemId() );
	  System.out.println(request.getDesc());
	  System.out.println(request.getMydata().getDesc());
	  return request;
  }
  public String hello()
  {
	  return _greeting;
  }
}