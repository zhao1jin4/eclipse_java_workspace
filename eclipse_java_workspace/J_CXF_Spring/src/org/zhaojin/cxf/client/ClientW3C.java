package org.zhaojin.cxf.client;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
public class ClientW3C
{
	
	public static void main(String[] args) throws Exception 
	{
	
		
		//String NAMESPACE_URI = "http://server.spring.cxf.zhaojin.org/";
		//String URL = "http://localhost:8080/J_CXF_Spring/ws/HelloWorld";
		//String PARAM_NAME="arg0";
		//---
		String NAMESPACE_URI = "http://cxf.zhaojin.org/";
		String URL = "http://localhost:8000/helloWorld";
		String PARAM_NAME="text";
		//---
		
		String PREFIX = "tns";
		String REQ_NAME="sayHi";
		String RES_NAME="sayHiResponse";
		String HELLO="Jack";
		try {
				
			SOAPMessage requestMsg = MessageFactory.newInstance().createMessage();
			requestMsg.setProperty(SOAPMessage.WRITE_XML_DECLARATION,"true");
			requestMsg.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
		        
			SOAPEnvelope envelope = requestMsg.getSOAPPart().getEnvelope();
			/*
			 {//方式一
				Name helloRequestName = envelope.createName(REQ_NAME, PREFIX, NAMESPACE_URI);
				
				SOAPBodyElement helloRequestElement = requestMsg.getSOAPBody().addBodyElement(helloRequestName);
				SOAPElement param=	helloRequestElement.addChildElement(PARAM_NAME);
				param.setValue(HELLO);
				
				requestMsg.writeTo(System.out);
				System.out.println();
	//			<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
	//				<SOAP-ENV:Header/>
	//				<SOAP-ENV:Body>
	//					<tns:sayHi xmlns:tns="http://server.webservice.zhaojin.org/">
	//						<text>Jack</text>
	//					</tns:sayHi>
	//				</SOAP-ENV:Body>
	//			</SOAP-ENV:Envelope>
			}*/
			{//方式二
				SOAPElement  ns=envelope.addNamespaceDeclaration(PREFIX, NAMESPACE_URI);//加名称空间
				Name helloRequestName = envelope.createName(PREFIX+":"+REQ_NAME);//使用
				
				SOAPBodyElement helloRequestElement = requestMsg.getSOAPBody().addBodyElement(helloRequestName);
				SOAPElement param=	helloRequestElement.addChildElement(PARAM_NAME);
				param.setValue(HELLO);
				
				requestMsg.writeTo(System.out);
//				<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
//						xmlns:tns="http://cxf.zhaojin.org/">
//						<SOAP-ENV:Header />
//						<SOAP-ENV:Body>
//							<tns:sayHi>
//								<text>Jack</text>
//							</tns:sayHi>
//						</SOAP-ENV:Body>
//					</SOAP-ENV:Envelope>
				System.out.println();
			}
			
			SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
			 
			
			//这里可以设置超时时间
	         URL url = new URL( null,  "http://127.0.0.1:8080",  new URLStreamHandler()
	         { 
	             @Override
	             protected URLConnection openConnection(URL url) throws IOException 
	             {
	                 URL clone_url = new URL(url.toString());
	                 HttpURLConnection clone_urlconnection = (HttpURLConnection) clone_url.openConnection();
	                 clone_urlconnection.setConnectTimeout(10000);
	                 clone_urlconnection.setReadTimeout(10000);
	                 return clone_urlconnection ;
	             }
	        });
	        SOAPMessage soapRespone=   connection.call(requestMsg, url);
	         
	        
	        
			SOAPMessage responseMsg = connection.call(requestMsg, URL);
			responseMsg.writeTo(System.out);
			System.out.println();
//			<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
//				<soap:Body>
//					<ns1:sayHiResponse xmlns:ns1="http://server.webservice.zhaojin.org/">
//						<return>Hello Jack</return>
//					</ns1:sayHiResponse>
//				</soap:Body>
//			</soap:Envelope>
			if (!responseMsg.getSOAPBody().hasFault())
			{
				envelope = responseMsg.getSOAPPart().getEnvelope();
				Name helloResponseName = envelope.createName(RES_NAME, PREFIX, NAMESPACE_URI);
				Iterator childElements = responseMsg.getSOAPBody().getChildElements(helloResponseName);
				SOAPBodyElement helloResponseElement = (SOAPBodyElement) childElements.next();
				String value = helloResponseElement.getTextContent();
				System.out.println("\nHello Response [" + value + "]");
			} else
			{
				SOAPFault fault = responseMsg.getSOAPBody().getFault();
				System.err.println("SOAP Fault Code :" + fault.getFaultCode());
				System.err.println("SOAP Fault String :" + fault.getFaultString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
