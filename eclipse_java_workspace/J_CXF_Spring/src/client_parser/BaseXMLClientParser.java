package client_parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BaseXMLClientParser 
{
	public   Logger log = LoggerFactory.getLogger(this.getClass());

	private SOAPMessage convertString2SOAP(String classPathFile) throws Exception 
	{
		BufferedReader reader=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(classPathFile)));
		String line;
		StringBuffer soapBuffer=new StringBuffer();
		while((line=reader.readLine())!=null)
		{
			soapBuffer.append(line).append("\n");
		}
		 SOAPMessage reqMsg = MessageFactory.newInstance().createMessage(new MimeHeaders(), new ByteArrayInputStream(soapBuffer.toString().getBytes(Charset.forName("UTF-8"))));
		 reqMsg.saveChanges();
		 //reqMsg.writeTo(System.out);
		 return reqMsg;
	}
	
	private String convertSOAP2String(SOAPMessage msg,String respCharset) throws Exception
	{
       SOAPPart soapPart=msg.getSOAPPart();//如设置返回报文的字符集,让下一步可成功执行????
        Document doc = soapPart.getEnvelope().getOwnerDocument();
        Source sourceContent=new DOMSource(doc);
//        Source sourceContent = msg.getSOAPPart().getContent();//方式二,会转换为当前.java的字符集
        
		StringWriter output = new StringWriter();
		Transformer   transformer=TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
		transformer.transform(sourceContent, new StreamResult(output));
		//System.out.println(output.toString());
		return output.toString();
	}
	 
	
	private SOAPMessage convertString2SOAP(String classPathFile,Map<String,String> param)  throws Exception
	{
		String prefix="${";
		String suffix="}";
		
		InputStream input=this.getClass().getResourceAsStream(classPathFile);
		if(input==null)
		{
			log.error("========error====="+classPathFile+" 找不到");
			throw new FileNotFoundException(classPathFile+"找不到");
		}
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(input));
			
		String line;
		StringBuffer soapBuffer=new StringBuffer();
		
			while((line=reader.readLine())!=null)
			{
				if(param!=null && line.contains(prefix) ) 
				{
					String key=line.substring(line.indexOf(prefix)+prefix.length(), line.indexOf(suffix));
					if(param.get(key)!=null)
					{
						line=line.replace( prefix+key+suffix  , param.get(key));
					}else
					{
						log.error("========error=====文件classPathFile中的变量   "+key+"  未配置");
						throw new Exception("文件classPathFile中的变量   "+key+"  未配置");
					}
					
				}
				soapBuffer.append(line).append("\n");
			}
		
		 SOAPMessage reqMsg = MessageFactory.newInstance().createMessage(new MimeHeaders(), new ByteArrayInputStream(soapBuffer.toString().getBytes(Charset.forName("UTF-8"))));
         reqMsg.saveChanges();
         //log.info(classPathFile+"解析后的请求内容为----------\n"+soapBuffer);
         //reqMsg.writeTo(System.out);
         return reqMsg;
	}
	
	protected String invokeWebService(String soapUIFile,String serviceURL,Map<String,String> param) throws Exception 
	{ 
		initLog4j();
		
		SOAPMessage reqMsg =convertString2SOAP(soapUIFile,param);
		SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
		SOAPMessage responseMsg = connection.call(reqMsg, serviceURL);
		String res=convertSOAP2String(responseMsg,"UTF-8");
		return res;
	}
	protected  <T> T parseRequest(String classPathFile,Map<String,String> param,Class<T> type) throws Exception 
	{ 
		initLog4j();
		SOAPMessage  soap=this.convertString2SOAP(classPathFile, param);
		SOAPBody body=soap.getSOAPBody();
		Element wrongParam=(Element)body.getElementsByTagName("arg0").item(0);
		
		Document doc=wrongParam.getOwnerDocument();
		String simpleName=type.getSimpleName();//大写
		String lowerName=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1, simpleName.length());
		Element paramName=doc.createElement(lowerName);
		NodeList list=wrongParam.getChildNodes();
		for(int i=0;i<list.getLength();i++)
		{
			paramName.appendChild(list.item(i).cloneNode(true) );
		}
		
		StringWriter output = new StringWriter();
		Transformer   transformer=TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
		transformer.transform(new DOMSource(paramName), new StreamResult(output));
		log.info("---------解析的  [请求] 对象参数  \n"+output.toString());
		 
		JAXBContext context=JAXBContext.newInstance(type);  
		Unmarshaller unmarshaller=context.createUnmarshaller();
		
		T req=(T) unmarshaller.unmarshal(new StringReader(output.toString()));
		return req ;
	}
	private void initLog4j()
	{
//		System.setProperty("log_home","c:/log");
//		URL url=this.getClass().getResource("/client_parser/log4j.xml");
//		DOMConfigurator.configure(url);
	}
	protected String generateOrderNO(String name)
	{
		int random= 1+(int)(Math.random()*1000000000);
		String orderNo= name+random;
		if(orderNo.length()>32)
			orderNo=orderNo.substring(0, 31);
		 return orderNo;
	}
	 
}
