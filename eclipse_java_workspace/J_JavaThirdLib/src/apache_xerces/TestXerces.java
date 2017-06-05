
package apache_xerces;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestXerces
{
	public static void domWrite()throws Exception 
	{

		String outFile_xerces="c:/temp/xerces.xml";
		
	  //Exerse �����ʽ����  ,��ʱ�ĵ�,�Ƽ���DOM Level 3 LSSerializer ���� JAXP's Transformation 
	  OutputFormat   outputFormat   =   new   OutputFormat("XML","gb2312",true);  
	  FileWriter   fileWriter=new   FileWriter(new File(outFile_xerces));  
	  XMLSerializer   xmlSerializer=new   XMLSerializer(fileWriter,outputFormat);  
	  xmlSerializer.asDOMSerializer();  
		  
		org.w3c.dom.Document doc=new org.apache.xerces.dom.DocumentImpl();//����
		Element root=doc.createElement("root");
		Attr  attr=doc.createAttribute("name");
		attr.setValue("lisi"); 
		root.setAttributeNode(attr);
		root.appendChild(doc.createElement("person"));
		
		doc.appendChild(root);
		
//		child.appendChild(doc.getDocumentElement());//����������,һ��ֻ���Ա�һ�� document����
//		child.appendChild(doc.getDocumentElement().cloneNode(true));//isDeep  //����������,
		
		  xmlSerializer.serialize(doc.getDocumentElement());  
		  fileWriter.close();  
		  
		  //LSSerializer  ??????????????
		  LSSerializer x=null;
	}
	
	public static void domRead() throws Exception 
	{
			//xml-apis.jar,	xercesImpl.jar
			
			InputStream input=TestXerces.class.getResourceAsStream("/apache_xerces/rule.xml");
			InputSource source=new InputSource(input);
			DOMParser  parser = new DOMParser();//xerces
			parser.parse(source);
			input.close();
			
			Document doc=parser.getDocument();//ͬJAXP org.w3c.dom.Document
			Element root = doc.getDocumentElement();//<root>
			Node node=root.getFirstChild();
			if(node.getNodeType()==Node.TEXT_NODE)
				System.out.println(node.getNodeValue()+"="+node.getTextContent()+"�����հ�,���������հ�ʧ��,��");
	}
	public static void main(String[] args)throws Exception 
	{	
		//xercesImpl.jar/META-INF/services/javax.xml.parsers.DocumentBuilderFactory�ļ��м�¼��DocumentBuilderFactoryʵ����
		
		//domRead();
		domWrite();
	}

}