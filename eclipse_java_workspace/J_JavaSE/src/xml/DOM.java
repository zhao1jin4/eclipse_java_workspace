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
		 ����˳��
		1.����ϵͳ����javax.xml.parsers.DocumentBuilderFactory��ֵ,��System.getProperties,��java -D
		2.JRE/lib/����jax.properties�ļ�,дjava x.xml.parsers.DocumentBuilderFactory=ʵ��org.apache.xerces.jaxp.DocumentBuilderFactoryImpl
		3.classpath����/META-INF/services/javax.xml.parsers.DocumentBuilderFactory�ļ���дʵ����
		xercesImpl.jar/META-INF/services/javax.xml.parsers.DocumentBuilderFactory�ļ��м�¼��DocumentBuilderFactoryʵ����
		JDKĬ��ʹ��com.sun.org.apache.xerces �� xalan
		�滻JDKĬ�ϵ���JRE/lib/�½���endorsed��Ŀ¼��ʵ�ֵ�.jar������,��������ϵͳ����java.endorsed.dirs��ֵΪĿ¼����λ��
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
           //----û��????
//           Node soapBody1=dom.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Body").item(0);
//           Node soapBody2=dom.getElementsByTagNameNS("soapenv", "Body").item(0);
           //------------

           Node  soapBody=dom.getElementsByTagName("soapenv:Body").item(0); //w3c��Ҫ�����ƿռ�,dom4j�Ŀɲ��ô�
           requestStr=elementToString((Element)soapBody); //w3c ���ƿռ䶪��,dom4j�����ƿռ��
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
	        StreamResult   stream   =   new   StreamResult(stringWriter);//��������ΪOutputStream
	        Transformer   transformer=tFactory.newTransformer();
	        transformer.setOutputProperty( "encoding", "UTF-8");  
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); 
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");

	        transformer.transform(source,stream); 

	        return stringWriter.toString();
	    }
}