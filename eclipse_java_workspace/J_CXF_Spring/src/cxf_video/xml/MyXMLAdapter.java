package cxf_video.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MyXMLAdapter extends XmlAdapter<String,Address>{

	public Address unmarshal(String v) throws Exception 
	{
		String street=v.split(",")[0];
		int no=Integer.parseInt(v.split(",")[1]);
		Address addr=new AddressImpl(street,no);
		return addr;
	}

	// Java->XML的 marshal
	public String marshal(Address v) throws Exception {
		return v.getStreet()+","+v.getNo();//不用写<>
	}

}
