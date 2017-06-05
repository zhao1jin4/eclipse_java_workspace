package xtream;

import java.util.ArrayList;
import java.util.List;


public class XStreamMain {

	public static void main(String[] args) {
		MyRequest req=new MyRequest();
		
		MyHeader head=new MyHeader();
		head.setVersion("2.0");
		head.setSignature("123123===");
		req.setHead(head);
		
		
		List<MyBody> listBody=new ArrayList<MyBody>();
		MyBody body=new MyBody();
		body.setId("111");
		body.setName("¿Ó");
		listBody.add(body);
		
		MyBody body2=new MyBody();
		body2.setId("222");
		body2.setName("¿Ó222");
		listBody.add(body2);
		
		req.setBody(listBody);
		String xml=XmlUtil.objToXml(req);
		System.out.println(xml);

		MyRequest req2=XmlUtil.xmlToObj(xml,MyRequest.class);
		System.out.println(req2.getHead().getVersion());
		System.out.println(req2.getBody().get(0).getName());
		
	}

}
