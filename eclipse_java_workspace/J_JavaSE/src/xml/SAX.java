package xml;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class SAX {
	public static void saxRead()
	{
		try
		{
			//xercesImp.jar/META-INF/services/java.xml.parsers.SAXParserFactory文件中记录实现类
			
			//SAX2  XMLReaderFactory jdk9过时 ，用SAXParserFactory
//			InputStream input=SAX.class.getResourceAsStream("/xml/rule.xml");
//			InputSource source=new InputSource(input);
//			XMLReader xmlReader=XMLReaderFactory.createXMLReader();//org.xml包  
//			xmlReader.setFeature("http://xml.org/sax/features/validation", true);//打开DTD验证
//			xmlReader.setContentHandler(new MyContenttHandler());
//			xmlReader.setErrorHandler(new MyErrorHandler());
//			xmlReader.parse(source);
			//input.close();//会自动close InputStream
			
			
			//SAX1
			SAXParserFactory spf = SAXParserFactory.newInstance();//javax.xml包
			SAXParser sp = spf.newSAXParser();
			sp.parse(new InputSource(SAX.class.getResourceAsStream("/xml/rule.xml")), new MyContenttHandler());
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		saxRead();
	}
}

class MyErrorHandler extends DefaultHandler 
{

	public void error(SAXParseException ex) throws SAXException {
		System.out.println("error:"+ex.getMessage());
	}
	public void fatalError(SAXParseException ex) throws SAXException {
		System.out.println("fatalError:"+ex.getMessage());
	}
	public void warning(SAXParseException ex) throws SAXException {
		System.out.println("warning:"+ex.getMessage());
	}
}
class MyContenttHandler extends DefaultHandler 
{
	public void startDocument() throws SAXException {
		System.out.println("start document");
	}
	public void endDocument() throws SAXException {
		System.out.println("end document");
	}
	public void startElement(String uri, String localName, String qName, Attributes attributes)  throws SAXException {
		System.out.print("<"+qName);
		for (int i=0;i<attributes.getLength();i++)
		{
			System.out.print(" "+attributes.getQName(i)+"='"+attributes.getValue(i)+"'");
		}
		System.out.println(">");
	}
	public void endElement(String uri, String localName, String qName) 
			throws SAXException {
		System.out.println("</"+qName+">");
	}
	
}