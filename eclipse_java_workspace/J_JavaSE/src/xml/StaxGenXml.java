package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class StaxGenXml {
	/**
	 * StAX����XML������The Streaming API for XML��� JDK1.6������
	 * 
	 * @param list
	 * @return
	 */
	public static void main(String[] args) {
		List<Employee> list = new ArrayList<Employee>();
		for (int i = 0; i < 5; i++) {
			Employee emp = new Employee();
			emp.id = i + "_";
			if(i==3)
				emp.firstName = "";
			else
				emp.firstName = "name_" + i;
			
			list.add(emp);
		}
		String str = stAXToXml(list);
		System.out.println(str);
	}

	public static String stAXToXml(List<Employee> list)
	{
		if (null == list && !list.isEmpty()) {
			return "";
		}
		String xmlStr = null;
		//String enter=System.getProperty("line.separator");
		try {
			// �������ڻ�ȡ XMLEventWriter �� XMLStreamWriter �Ĺ�������ʵ��
			XMLOutputFactory xof = XMLOutputFactory.newInstance();
			StringWriter writerStr = new StringWriter();
			XMLStreamWriter xmlsw = xof.createXMLStreamWriter(writerStr);
			 
//			PrintWriter writerXml = new PrintWriter(new OutputStreamWriter(
//					new FileOutputStream("d:/StAX.xml"), "utf-8"));
//			XMLStreamWriter xmlsw = xof.createXMLStreamWriter(writerXml);
			
			xmlsw.writeStartDocument("UTF-8", "1.0");
			xmlsw.writeCharacters("\n");
			xmlsw.writeStartElement("employees");
			xmlsw.writeComment("ʡ�ͳ�����Ϣ");
			xmlsw.writeCharacters("\n");
		
			for (Employee po : list)
			{
				xmlsw.writeCharacters(" ");
				xmlsw.writeStartElement("employee");
				xmlsw.writeCharacters("\n");
				
//				xmlsw.writeAttribute("id", String.valueOf(po.id));//����???
				
				// ���<id>�ڵ�
//				xmlsw.writeCharacters("   ");
//				xmlsw.writeStartElement("id");
//				xmlsw.writeCharacters(String.valueOf(po.id));
//				xmlsw.writeEndElement();
//				xmlsw.writeCharacters("\n");
				
				// ���<name>�ڵ�
				xmlsw.writeCharacters("   ");
				if(po.firstName == null || po.firstName.trim().equals(""))
				{
					xmlsw.writeEmptyElement("name");
				}else
				{
					xmlsw.writeStartElement("name");
					xmlsw.writeCharacters(po.firstName);
					xmlsw.writeEndElement();
				}
				xmlsw.writeCharacters("\n");

				//end employee
				xmlsw.writeCharacters(" ");
				xmlsw.writeEndElement();
				xmlsw.writeCharacters("\n");
			}
			// ����<employees>�ڵ�
			xmlsw.writeEndElement();
			xmlsw.writeCharacters("\n");
			
			// ���� XML �ĵ�
			xmlsw.writeEndDocument();
			xmlsw.flush();
			xmlsw.close();

			xmlStr = writerStr.getBuffer().toString();
			writerStr.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("StAX:" + xmlStr);
		return xmlStr;
	}
}
