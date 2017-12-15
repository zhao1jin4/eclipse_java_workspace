package dom4j;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.VisitorSupport;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
public class TestDom4j
{
	public static void main(String[] args) throws Exception
	{
		TestDom4j x=new TestDom4j();
		Document doc =x.createDocument();
		//x.write(doc);
		//x.parseXMLSAX();
	
		x.testParseNamespace();
		//x.w3cDom__dom4jDom();
	}
	public void testParseNamespace() throws Exception
	{ 
	    StringBuffer xmlSoapResponse=new StringBuffer();
	    InputStream input=TestDom4j.class.getResourceAsStream("/dom4j/soapResp.xml");
        BufferedReader reader=new BufferedReader(new InputStreamReader(input));
        String line;
	    while((line=reader.readLine())!=null)
	    {
	        xmlSoapResponse.append(line);   
	    }
	    reader.close();
	    
        Document document = DocumentHelper.parseText(xmlSoapResponse.toString());
        org.dom4j.Element soapenvNode = document.getRootElement().element("Body").element("respone1");//dom4j�ɲ������ƿռ�
        String xmlResponse=soapenvNode.asXML();//dom4j�ᱣ�����ƿռ�
        System.out.println(xmlResponse);
	}
    public Document createDocument() throws IOException 
    {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "root" );

        Element author1 = root.addElement( "author" )
            .addAttribute( "name", "James" )
            .addAttribute( "location", "UK" )
            .addText( "James Strachan" );
        
        Element author2 = root.addElement( "author" )
            .addAttribute( "name", "Bob" )
            .addAttribute( "location", "US" )
            .addText( "Bob McWhirter" );
        
        return document;
    }
    public void write(Document document) throws IOException 
    {

    	Element root=document.getRootElement();
		System.out.println( document.asXML());//��<?xml 
		System.out.println(root.asXML());//��<?xml 
		FileWriter out= new FileWriter( "c:/temp/output.xml" );
//		root.write(out);//����XML����һ����
          
           
    	OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
    	xmlFormat.setEncoding("UTF-8");//GBK
    	xmlFormat.setIndent(true);//"�����Ǽ����ո�"
        XMLWriter writer = new XMLWriter(out ,xmlFormat);//�Ǹ�ʽ�õ�
        writer.write( document );//��<?xml
        //writer.write( root );//��<?xml
        out.close();
        writer.close();

    }
    public void parseXMLSAX() throws Exception
    {
    	FileReader in= new FileReader( "c:/temp/output.xml" );
    	SAXReader reader=new SAXReader();
    	Document doc=reader.read(in);//��<?xml,��<?xml ���ɽ���
    	
    	java.util.List list=doc.selectNodes("//author[@name='James']");//Ҫorg/jaxen/
    	for(int i=0;i<list.size();i++)
    	{
    		System.out.println(((Element)list.get(i)).getData());
    	}
    	
    	
		Element root=doc.getRootElement();
		System.out.println("���ڵ�����"+root.getName());
		//root.elementByID("")
		List<Element> authors=root.elements();
		for(Iterator<Element> it= authors.iterator();it.hasNext();)
		{
			Element author= it.next();
			Attribute attr=author.attribute("name");
			System.out.println("���ԣ�"+attr.getValue());
			System.out.println("�ı���"+author.getText());
		}
		in.close();
    }
    public void w3cDom__dom4jDom() throws Exception
    {
    	InputStream input=new FileInputStream("c:/temp/output.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		factory.setValidating(true);//��XML��DTD��֤,�����setIgnoringElementContentWhitespace����
		//factory.setSchema()//schema��֤
//		factory.setIgnoringElementContentWhitespace(true);//���������հ�,��Ч��??????????
		DocumentBuilder db = factory.newDocumentBuilder();
		org.w3c.dom.Document doc = db.parse(input);
    	//---dom4j
    	DOMReader domReader=new DOMReader();
    	org.dom4j.Document doc4j=domReader.read(doc);//org.w3c.dom.Document -> org.dom4j.Document
//    	doc4j.accept(new MyVisitor());//��ʽһ
    	Element root=doc4j.getRootElement();//��ʽ��
    	java.util.List list=root.selectNodes("//author[@name='James']");//Ҫorg/jaxen/
    	for(int i=0;i<list.size();i++)
    	{
    		System.out.println(((Element)list.get(i)).getData());
    	}
    }
    class MyVisitor extends VisitorSupport
    {
		public void visit(Attribute node) {
			System.out.println("���ԣ�"+node.getName()+"="+node.getValue());
		}
		public void visit(Element node) {
			if(node.isTextOnly())
				System.out.println("Ԫ�أ�"+node.getName()+" > "+node.getText());
			else
				System.out.println("Ԫ�أ�"+node.getName());
		}
		public void visit(ProcessingInstruction node) {//ָ��,������XSL
			System.out.println("ͷ��"+node.getTarget());
		}
    }
}
