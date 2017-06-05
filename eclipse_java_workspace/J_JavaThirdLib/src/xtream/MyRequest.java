package xtream;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Request")
public class MyRequest 
{
	@XStreamAlias("Head")
	private MyHeader head;
	
	@XStreamImplicit(itemFieldName = "Body")
	private List<MyBody> body=new ArrayList<MyBody>();

	public MyHeader getHead() {
		return head;
	}

	public void setHead(MyHeader head) {
		this.head = head;
	}

	public List<MyBody> getBody() {
		return body;
	}

	public void setBody(List<MyBody> body) {
		this.body = body;
	}

}
