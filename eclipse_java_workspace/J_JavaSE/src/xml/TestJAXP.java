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
		//factory.setValidating(true);//��XML��DTD��֤,�����setIgnoringElementContentWhitespace����
		//factory.setSchema()//schema��֤
		factory.setIgnoringElementContentWhitespace(true);//���������հ�,��Ч��??????????
		DocumentBuilder db = factory.newDocumentBuilder();
		db.setErrorHandler(new ErrorHandler(){ //�� setValidating(true)ʱ�д���,���������쳣
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
		Document doc = db.parse(input);//<?xml ���п���
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
			System.out.println(node.getNodeValue()+"="+node.getTextContent()+"�����հ�,���������հ�ʧ��,��");
		
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
		
		//дDocument ,javax   
		TransformerFactory   tFactory=TransformerFactory.newInstance();
		String f=System.getProperty("javax.xml.transform.TransformerFactory");//org.apache.xalan.processor.TransformerFactoryImpl,ΪnullҲ����
		Transformer   transformer=tFactory.newTransformer();//new StreamSource("")�ɴ��ļ�,�ɰ�Stream->Source
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");//��ҪXML����
		//<xsl:output ������
		transformer.setOutputProperty( OutputKeys.ENCODING, "UTF-8");  //"encoding"
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");//����ʹ��Xalan-J��Ҫ��indent=yesһ��ʹ��
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		
		javax.xml.parsers.DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//javax
		DocumentBuilder db = factory.newDocumentBuilder();
		Document doc=db.newDocument();//����
		Element root=doc.createElement("root");
		Attr  attr=doc.createAttribute("name");
		attr.setValue("lisi"); 
		root.setAttributeNode(attr);
		root.appendChild(doc.createElement("person"));//��<person/>��ʽ
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
