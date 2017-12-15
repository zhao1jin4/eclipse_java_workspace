package client_parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestClient extends BaseXMLClientParser {

	@Test
	public void testParserUseDomCopy() throws Exception
	{
		Map<String,String> param=new HashMap<String,String>();
		param.put("transId", "12312312");
		PaymentReq req = parseRequest("/client_parser/execPay_agreePay.xml",param,PaymentReq.class);
		System.out.println("对象为:"+req);
	}
	@Test
	public void testParserUseCXF() throws Exception
	{
		 
	}
}
