
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
		
	  //Exerse 输出格式良好  ,过时的的,推荐用DOM Level 3 LSSerializer 或者 JAXP's Transformation 
	  OutputFormat   outputFormat   =   new   OutputFormat("XML","gb2312",true);  
	  FileWriter   fileWriter=new   FileWriter(new File(outFile_xerces));  
	  XMLSerializer   xmlSerializer=new   XMLSerializer(fileWriter,outputFormat);  
	  xmlSerializer.asDOMSerializer();  
		  
		org.w3c.dom.Document doc=new org.apache.xerces.dom.DocumentImpl();//建立
		Element root=doc.createElement("root");
		Attr  attr=doc.createAttribute("name");
		attr.setValue("lisi"); 
		root.setAttributeNode(attr);
		root.appendChild(doc.createElement("person"));
		
		doc.appendChild(root);
		
//		child.appendChild(doc.getDocumentElement());//不可以这样,一个只可以被一个 document创建
//		child.appendChild(doc.getDocumentElement().cloneNode(true));//isDeep  //不可以这样,
		
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
			
			Document doc=parser.getDocument();//同JAXP org.w3c.dom.Document
			Element root = doc.getDocumentElement();//<root>
			Node node=root.getFirstChild();
			if(node.getNodeType()==Node.TEXT_NODE)
				System.out.println(node.getNodeValue()+"="+node.getTextContent()+"包含空白,忽略缩进空白失败,可");
	}
	public static void main(String[] args)throws Exception 
	{	
		//xercesImpl.jar/META-INF/services/javax.xml.parsers.DocumentBuilderFactory文件中记录着DocumentBuilderFactory实现类
		
		//domRead();
		domWrite();
	}

}