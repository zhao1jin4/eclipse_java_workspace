package quiz;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class TestFinal {

	public static  void  test(final Document doc)// List<final String> ��������ʹ��
	{
		//doc=null;//��������
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
		doc=null;//���Ե�
		
		
		TestFinal t=new TestFinal();
		t.new Child().test();
		
		
		
		StringBuffer x;  
		
		
		String str="asdbc";
		str.length();
		
		FinalMemeber finalMember=new FinalMemeber();
		finalMember.length=5;
		
	}
	
	
	interface   I//�ɱ����implements
	{
		int a=0;	//����Ĭ����public static final,�����ʼ��,Ҳֻ����public static final
		public abstract void test();//�ӿڵķ������� ��public,abstract���п���
	}
	abstract class C implements I//ֻ�ɱ�һ��extends
	{
		int b;
		protected	abstract  void one();//������ķ������Բ���public
		
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
