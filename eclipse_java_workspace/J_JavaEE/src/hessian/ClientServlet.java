package hessian;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caucho.hessian.client.HessianProxyFactory;

//@WebServlet(urlPatterns={"/hessian/client"})
@WebServlet( "/hessian/client" )
public class ClientServlet  extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		String url = "http://127.0.0.1:8080/J_JavaEE/hessian/server";
		HessianProxyFactory factory = new HessianProxyFactory();
		BasicAPI basic = (BasicAPI) factory.create(BasicAPI.class, url);
		System.out.println("====Hessian=====hello(): " + basic.hello());
		
		MyRequest reqObj=new MyRequest(); //如传对象必须  implements  Serializable
		reqObj.setSystemId("123");
		reqObj.setSystemName("boss");
		reqObj.setDesc("objDesc");
		DataObj data=new DataObj();
		data.setDesc("dataDesc");
		data.setDataId("dataId");
		reqObj.setMydata(data);
		BigDecimal amt=BigDecimal.valueOf(22.35d);//要用BigDecimal.valueOf()不要用new BigDecimal()
		BigDecimal amt1=new BigDecimal(22.35d);
		System.out.println("====client Amt: " + amt);
		System.out.println("====client Amt1: " + amt1);
		reqObj.setAmt(amt);
		
		
		reqObj.setStartDate(new java.util.Date());

		basic.setObject(reqObj);
		
		System.out.println("====Hessian=====server changed SystemId: " + basic.getObject().getSystemId());
		
		MyRequest resObj=basic.getObject();
		
		System.out.println(resObj.getDesc()+":"+resObj.getMydata().getDesc());
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
	}
	
}
