package cxf_video.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;



public class JAXBMain {
    
    public void jaxbOutputStream()throws Exception 
    {
        JAXBContext context=JAXBContext.newInstance(Boy.class); //类名,也可以是包名,下的所有的类
        Marshaller marshaller=context.createMarshaller();   //Java->XML的
        Unmarshaller unmarshaller=context.createUnmarshaller(); //XML->Java的
        Boy body=new Boy();
        //body.setName("张三");
        Address addr=new AddressImpl("张江路",40);
        body.setAddr(addr);
        
        
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT , true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);//true 不要<?xml 头
        marshaller.marshal(body,System.out);//如字段值为null,标签就不会出现,如何要出现????
        
        
        Boy boy2=(Boy)unmarshaller.unmarshal(new StringReader("<ns:myroot xmlns:ns='http://mynamespace'><age>10</age><addr>张江路,40</addr><name>A</name></ns:myroot>"));//可以是很多类型的XML
        System.out.println("\n"+boy2.getName()+","+boy2.getAddr().getStreet());
        
        
    }
	public static void main(String[] args) throws Exception 
	{
	
	    JAXBMain main=new JAXBMain();
	    main.jaxbOutputStream();
	    //main.jaxbOutSoap();
		
	}

	private  void   jaxbOutSoap() throws Exception 
	{
	    
	    Boy body=new Boy();
        //body.setName("张三");
        Address addr=new AddressImpl("张江路",40);
        body.setAddr(addr);
        
        List<String> favorites=new ArrayList<String>();
        favorites.add("玩游戏");
        favorites.add("打球 ");
        body.setFavorites(favorites);
        
        Family family =new Family();
        family.setFather("father-name");
        family.setMather("mather-name");
        body.setFamily(family);
        String remoteUrl="http://127.0.0.1:8080";
        
	    SOAPMessage soapRequest= SoapXmlUtil.bindToSoap(body);
	    String logRequest = SoapXmlUtil.soapToString(soapRequest,Charset.forName("UTF-8"));
	    System.out.println(" 请求报文 "+logRequest);
	    URL url=SoapXmlUtil.createTimeOutURL(remoteUrl, 2000);
	    
         SOAPMessage soapResponse=  SoapXmlUtil.sendMsg(soapRequest, url);
        //------处理返回
         String xmlSoapResponse  = SoapXmlUtil.soapToString(soapResponse,Charset.forName("GBK"));
         String formatXml= SoapXmlUtil.formatXml(logRequest);
         System.out.println( "response message "+formatXml);
         xmlSoapResponse=SoapXmlUtil.changeXmlDelaration2UTF8(xmlSoapResponse);
        
         String responseObjectXml =SoapXmlUtil.getXmlStrElementByTagName(xmlSoapResponse, "myroot");
         Boy respone= SoapXmlUtil.xmlToBind(responseObjectXml, Boy.class);
         //------ 中文处理 这条路不通???
         //服务端返回的HTTP头没指定字符集 ,xml头指定了,不会使用,默认就是和java当前字符集处理
         //因返回的是GBK,而.java是UTF-8,不一致所以错误?????,没有地可以修改?????
         String responseStr=convertSOAP2String(soapResponse,Charset.forName("GBK")) ;
         if (!soapResponse.getSOAPBody().hasFault()) //中文处理不解决 ,不能getSOAPBody()  ????
         {
             
         }
	}
 
   public String convertSOAP2String(SOAPMessage msg,Charset respCharset) throws Exception
   {
       SOAPPart soapPart=msg.getSOAPPart();//如设置返回报文的字符集,让下一步可成功执行????
//     soapPart.setMimeHeader("Content-Type", "text/xml; charset=gbk");//这里设置返回报文的字符集?????好像不对????
       soapPart.setMimeHeader("charset", "gbk");//这里设置返回报文的字符集?????好像不对????
       
       Document doc = soapPart.getEnvelope().getOwnerDocument();//中文字符集和.java字符不同,返回报文带<?xml有中文编码,就不行??? 没地设置????????
       Source sourceContent=new DOMSource(doc);
//       Source sourceContent = msg.getSOAPPart().getContent();//方式二,中文处理 没问题,会转换为当前.java的字符集
       
       StringWriter output = new StringWriter();
       Transformer   transformer=TransformerFactory.newInstance().newTransformer();
       transformer.setOutputProperty(OutputKeys.INDENT, "yes");
       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
       transformer.transform(sourceContent, new StreamResult(output));
       //System.out.println(output.toString());
       return output.toString();
   } 
	 
 
	 
}
