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
        org.dom4j.Element soapenvNode = document.getRootElement().element("Body").element("respone1");//dom4j可不会名称空间
        String xmlResponse=soapenvNode.asXML();//dom4j会保留名称空间
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
		System.out.println( document.asXML());//有<?xml 
		System.out.println(root.asXML());//无<?xml 
		FileWriter out= new FileWriter( "c:/temp/output.xml" );
//		root.write(out);//所有XML都在一行上
          
           
    	OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
    	xmlFormat.setEncoding("UTF-8");//GBK
    	xmlFormat.setIndent(true);//"或者是几个空格"
        XMLWriter writer = new XMLWriter(out ,xmlFormat);//是格式好的
        writer.write( document );//有<?xml
        //writer.write( root );//无<?xml
        out.close();
        writer.close();

    }
    public void parseXMLSAX() throws Exception
    {
    	FileReader in= new FileReader( "c:/temp/output.xml" );
    	SAXReader reader=new SAXReader();
    	Document doc=reader.read(in);//无<?xml,有<?xml 都可解析
    	
    	java.util.List list=doc.selectNodes("//author[@name='James']");//要org/jaxen/
    	for(int i=0;i<list.size();i++)
    	{
    		System.out.println(((Element)list.get(i)).getData());
    	}
    	
    	
		Element root=doc.getRootElement();
		System.out.println("根节点名："+root.getName());
		//root.elementByID("")
		List<Element> authors=root.elements();
		for(Iterator<Element> it= authors.iterator();it.hasNext();)
		{
			Element author= it.next();
			Attribute attr=author.attribute("name");
			System.out.println("属性："+attr.getValue());
			System.out.println("文本："+author.getText());
		}
		in.close();
    }
    public void w3cDom__dom4jDom() throws Exception
    {
    	InputStream input=new FileInputStream("c:/temp/output.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		factory.setValidating(true);//打开XML的DTD验证,后面的setIgnoringElementContentWhitespace才有
		//factory.setSchema()//schema验证
//		factory.setIgnoringElementContentWhitespace(true);//忽略缩进空白,无效果??????????
		DocumentBuilder db = factory.newDocumentBuilder();
		org.w3c.dom.Document doc = db.parse(input);
    	//---dom4j
    	DOMReader domReader=new DOMReader();
    	org.dom4j.Document doc4j=domReader.read(doc);//org.w3c.dom.Document -> org.dom4j.Document
//    	doc4j.accept(new MyVisitor());//方式一
    	Element root=doc4j.getRootElement();//方式二
    	java.util.List list=root.selectNodes("//author[@name='James']");//要org/jaxen/
    	for(int i=0;i<list.size();i++)
    	{
    		System.out.println(((Element)list.get(i)).getData());
    	}
    }
    class MyVisitor extends VisitorSupport
    {
		public void visit(Attribute node) {
			System.out.println("属性："+node.getName()+"="+node.getValue());
		}
		public void visit(Element node) {
			if(node.isTextOnly())
				System.out.println("元素："+node.getName()+" > "+node.getText());
			else
				System.out.println("元素："+node.getName());
		}
		public void visit(ProcessingInstruction node) {//指令,如引用XSL
			System.out.println("头："+node.getTarget());
		}
    }
}
