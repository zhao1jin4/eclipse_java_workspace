package quiz;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class TestFinal {

	public static  void  test(final Document doc)// List<final String> 不可这样使用
	{
		//doc=null;//不可这样
		doc.createElement("a");
		StringWriter w=new StringWriter();
		try {
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws ParserConfigurationException {
		Document doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument(); 
		test(doc);
		doc=null;//可以的
		
		
		TestFinal t=new TestFinal();
		t.new Child().test();
		
		
		
		StringBuffer x;  
		
		
		String str="asdbc";
		str.length();
		
		FinalMemeber finalMember=new FinalMemeber();
		finalMember.length=5;
		
	}
	
	
	interface   I//可被多次implements
	{
		int a=0;	//属性默认是public static final,必须初始化,也只能是public static final
		public abstract void test();//接口的方法必须 是public,abstract可有可无
	}
	abstract class C implements I//只可被一次extends
	{
		int b;
		protected	abstract  void one();//抽像类的方法可以不是public
		
	}
	class Child extends C implements I   
	{
		public void test()
		{
			System.out.println("in child");
		}
		protected void one()
		{
			int x=I.a;
			this.b=3;
		}
	}
	
}


final class FinalMemeber
{
	int length;
	
}
