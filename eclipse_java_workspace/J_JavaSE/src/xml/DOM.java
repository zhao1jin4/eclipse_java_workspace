package xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;



public class DOM {
	 public static void main(String[] args) throws Exception {
		/*
		 查找顺序
		1.设置系统属性javax.xml.parsers.DocumentBuilderFactory的值,如System.getProperties,或java -D
		2.JRE/lib/建立jax.properties文件,写java x.xml.parsers.DocumentBuilderFactory=实现org.apache.xerces.jaxp.DocumentBuilderFactoryImpl
		3.classpath下找/META-INF/services/javax.xml.parsers.DocumentBuilderFactory文件中写实现类
		xercesImpl.jar/META-INF/services/javax.xml.parsers.DocumentBuilderFactory文件中记录着DocumentBuilderFactory实现类
		JDK默认使用com.sun.org.apache.xerces 和 xalan
		替换JDK默认的在JRE/lib/下建立endorsed子目录将实现的.jar包放入,或者设置系统属性java.endorsed.dirs的值为目录所在位置
		*/
	     
	     
	    StringBuffer xmlSoapResponse=new StringBuffer();
        InputStream input=DOM.class.getResourceAsStream("/xml/soapResp.xml");
        BufferedReader reader=new BufferedReader(new InputStreamReader(input));
        String line;
        while((line=reader.readLine())!=null)
        {
            xmlSoapResponse.append(line);   
        }
        reader.close();
        
	     
        String soapXml=  DOM.getSoapXmlBodyContent(xmlSoapResponse.toString());
        System.out.println(soapXml);
	}		
	 public  static String   getSoapXmlBodyContent(String soapXml)
     {
         String requestStr=null;
       try
       {
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
           DocumentBuilder db = factory.newDocumentBuilder();
           Document dom = db.parse(new ByteArrayInputStream(soapXml.getBytes()));
           //----没用????
//           Node soapBody1=dom.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Body").item(0);
//           Node soapBody2=dom.getElementsByTagNameNS("soapenv", "Body").item(0);
           //------------

           Node  soapBody=dom.getElementsByTagName("soapenv:Body").item(0); //w3c的要带名称空间,dom4j的可不用带
           requestStr=elementToString((Element)soapBody); //w3c 名称空间丢了,dom4j用名称空间的
       } catch ( Exception e)
       { 
          e.printStackTrace();
       }
         return requestStr;
     }
	 
	  public static String elementToString(Element element) throws Exception
	    {
	        StringWriter stringWriter=new StringWriter();
	        TransformerFactory   tFactory=TransformerFactory.newInstance();
	        DOMSource   source=   new   DOMSource(element);
	        StreamResult   stream   =   new   StreamResult(stringWriter);//参数可以为OutputStream
	        Transformer   transformer=tFactory.newTransformer();
	        transformer.setOutputProperty( "encoding", "UTF-8");  
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); 
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");

	        transformer.transform(source,stream); 

	        return stringWriter.toString();
	    }
}