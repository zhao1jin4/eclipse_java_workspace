package cxf_video.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
  * Soap 工具类
  * @author zhaojin
  *
  */
public class SoapXmlUtil {

	
	public static <T> SOAPMessage   bindToSoap(Object request)  
	{ 
	    SOAPMessage soapRequest=null;
        try
        {
            JAXBContext context = JAXBContext.newInstance(request.getClass());
            Marshaller marshaller=context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); //true 不要<?xml 头
            
            soapRequest= MessageFactory.newInstance().createMessage();
            SOAPBody   soapBody=soapRequest.getSOAPBody(); 
            marshaller.marshal(request,soapBody); // 可marshal到 SOAPBody
//          marshaller.marshal(request,System.out);//如字段值为null,标签就不会出现,如何要出现????
        } catch (Exception e)
        {
            e.printStackTrace();
        }
     
       return soapRequest;
	}

	public static <T>   T  xmlToBind(String xmlString ,Class<T> clazz)  
    {
        T res=null;
        try
        {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller=context.createUnmarshaller();
            res=(T)unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
      
        return res;
    }
 

	public static  SOAPMessage convertString2SOAP(String xmlStr)  
    {
         SOAPMessage reqMsg=null;
        try
        {
            reqMsg = MessageFactory.newInstance().createMessage(new MimeHeaders(), new ByteArrayInputStream(xmlStr.toString().getBytes(Charset.forName("UTF-8"))));
            reqMsg.saveChanges();
        } catch ( Exception e)
        {
           e.printStackTrace();
        }
        
         return reqMsg;
    }
 
	public  static String convertSOAP2String(SOAPMessage msg) throws Exception//,Charset respCharet
    {
//	    SOAPPart soapPart=msg.getSOAPPart();
//	    soapPart.setMimeHeader("Content-Type", "text/xml; charset=gbk");//这里设置返回报文的字符集?????好像不对????
//	     soapPart.setMimeHeader("charset", "gbk");//这里设置返回报文的字符集?????好像不对????
	       
//        Document doc = soapPart.getEnvelope().getOwnerDocument();
//        Source sourceContent =new DOMSource(doc);
        Source sourceContent = msg.getSOAPPart().getContent();//方式二,无中文问题
        
        StringWriter output = new StringWriter();
        Transformer   transformer=TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
        transformer.transform(sourceContent, new StreamResult(output));
        //System.out.println(output.toString());
        return output.toString();
    }
	 
	public static SOAPMessage sendMsg(SOAPMessage soapMessage,URL url) throws SOAPException  
    {
	    SOAPConnection connection=null;
        
        SOAPConnectionFactory factory=SOAPConnectionFactory.newInstance();
        connection = factory.createConnection();
        
        soapMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION,"true");
        soapMessage.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
        
        SOAPMessage responseMsg = connection.call(soapMessage, url);
        return responseMsg;
    }
	public static String soapToString(SOAPMessage soapMessage,Charset charset) 
    {

        ByteArrayOutputStream output=new ByteArrayOutputStream();
        String res=null;
        try
        {
            soapMessage.writeTo(output);
            res=new String(output.toByteArray(),charset);  
            
        } catch ( Exception e)
        { 
           e.printStackTrace();
        }
      return res;
    }
	 public static String getXmlStrElementByTagName( String xmlStr,String elementName)
     {
         String resXml=null;
        try
        {
            //---dom4j
            Document document = DocumentHelper.parseText(xmlStr);
            Element soapenvNode = document.getRootElement().element("Body").element(elementName);//dom4j不用使用名称空间查
            resXml=soapenvNode.asXML();//dom4j会保留名称空间
        } catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return resXml; 
     }
 
	  public static String formatXml(String xml) throws Exception
	    {
	        Document document = DocumentHelper.parseText(xml);
	        OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
	        xmlFormat.setIndent(true); 
	        StringWriter resWriter=new StringWriter();
	        XMLWriter xmlWriter = new XMLWriter(resWriter,xmlFormat); 
	        xmlWriter.write(document.getRootElement()); 
	       
	        return resWriter.toString();
	    }
	public static String changeXmlDelaration2UTF8(String xml)
	   {
	       if(xml.contains("?>"))
	           xml=xml.substring(xml.indexOf("?>")+2);
	       xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml;
	       return xml;
	   }
	  
	public static URL createTimeOutURL(String url,final int timeout)
	{
	    URL resURL=null;
        try
        {
            resURL = new URL( null,  url,  new URLStreamHandler()
            { 
                @Override
                protected URLConnection openConnection(URL url) throws IOException 
                {
                    URL clone_url = new URL(url.toString());
                    HttpURLConnection clone_urlconnection = (HttpURLConnection) clone_url.openConnection();
                    clone_urlconnection.setConnectTimeout(timeout);
                    clone_urlconnection.setReadTimeout(timeout); //这里可以设置超时时间
                    return clone_urlconnection ;
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
	    
	    return resURL;
	}
	
}