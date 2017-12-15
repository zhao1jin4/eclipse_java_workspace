package xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType;

public class TestJAXP
{
	private static  Node ignoreXMLSpace(Node node)
	{
		if(node!=null)
			while(node.getNodeType()!=Node.ELEMENT_NODE)
			{
				node=node.getNextSibling();
				continue;
			}
		return node;
	}
	
	public static void domRead() throws Exception
	{
		InputStream input=TestJAXP.class.getResourceAsStream("/xml/rule.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//factory.setValidating(true);//打开XML的DTD验证,后面的setIgnoringElementContentWhitespace才有
		//factory.setSchema()//schema验证
		factory.setIgnoringElementContentWhitespace(true);//忽略缩进空白,无效果??????????
		DocumentBuilder db = factory.newDocumentBuilder();
		db.setErrorHandler(new ErrorHandler(){ //当 setValidating(true)时有错误,但不会抛异常
			@Override
			public void error(SAXParseException e) throws SAXException {
				System.out.println("error======="+e.getMessage());
			}
			@Override
			public void fatalError(SAXParseException e) throws SAXException {
				System.out.println("fatalError======="+e.getMessage());
			}
			@Override
			public void warning(SAXParseException e) throws SAXException {
				System.out.println("warning======="+e.getMessage());
			}});
		Document doc = db.parse(input);//<?xml 可有可无
		//Document doc = db.parse(  new ByteArrayInputStream("<root><rule></rule></root>".getBytes()));
		//input.close();
		
		doc.normalize();  
	        
		Element root = doc.getDocumentElement();//<root>
		

		 XPath xpath = XPathFactory.newInstance().newXPath();
	     XPathExpression expression = xpath.compile("//SQL[@Type='Value']");
	     NodeList nodeList = (NodeList)expression.evaluate(doc,XPathConstants.NODESET);
        for(int i = 0; i < nodeList.getLength(); i++){
            System.out.println(nodeList.item(i).getNodeName()+"="+nodeList.item(i).getTextContent());
        }
		
		Node node=root.getFirstChild();
		if(node.getNodeType()==Node.TEXT_NODE)
			System.out.println(node.getNodeValue()+"="+node.getTextContent()+"包含空白,忽略缩进空白失败,可");
		
		node=ignoreXMLSpace(node);
			
		NodeList allRule=doc.getElementsByTagName("rule");//<rule>
		int ruleLen=allRule.getLength(); 
		for(int k=0;k<ruleLen;k++)
		{
			Node rule=allRule.item(k);
			StringBuffer sql=new StringBuffer("select ");
			Node  transform=ignoreXMLSpace(rule.getFirstChild());//<Transform_Rule>
			
			System.out.println(transform.getTextContent());
		}
	}
	public static void domWrite() throws Exception
	{
		String outFile="C:/temp/jaxp.xml";
		
		//写Document ,javax   
		TransformerFactory   tFactory=TransformerFactory.newInstance();
		String f=System.getProperty("javax.xml.transform.TransformerFactory");//org.apache.xalan.processor.TransformerFactoryImpl,为null也可以
		Transformer   transformer=tFactory.newTransformer();//new StreamSource("")可传文件,可把Stream->Source
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");//不要XML声明
		//<xsl:output 的属性
		transformer.setOutputProperty( OutputKeys.ENCODING, "UTF-8");  //"encoding"
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");//对于使用Xalan-J的要和indent=yes一起使用
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		
		javax.xml.parsers.DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//javax
		DocumentBuilder db = factory.newDocumentBuilder();
		Document doc=db.newDocument();//建立
		Element root=doc.createElement("root");
		Attr  attr=doc.createAttribute("name");
		attr.setValue("lisi"); 
		root.setAttributeNode(attr);
		root.appendChild(doc.createElement("person"));//是<person/>格式
		doc.appendChild(root);
		
				
		DOMSource   source=   new   DOMSource(doc);
		StreamResult   stream   =   new   StreamResult(outFile);
		transformer.transform(source,stream);
	}
	public static void main(String[] args) throws Exception
	{
		 domRead();
		// domWrite();
	}
}
