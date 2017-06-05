package ws_provider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.handler.MessageContext;

public class Calc_DispatchClientW3C {
                                                                                                                                                                     
    public static SOAPMessage buildMessageForAdd(MessageFactory factory) throws SOAPException{
                                                                                                                                                                         
        SOAPMessage soapRequest = factory.createMessage();
                                                                                                                                                                         
        //构造<calcSum>元素，它的namespace为"http://services.server.cxfstudy.charles.com",注意这个元素在SOAPMessage的<soap:Body>部分
        QName calcSumQName = new QName("http://ws_provider/","calcSum"); //包名
        SOAPElement calcSumEle = soapRequest.getSOAPBody().addChildElement(calcSumQName);
        //在<calcSum>元素中添加2个子元素，一个为<a>3</a>,一个为<b>5</b>
        calcSumEle.addChildElement("a").addTextNode("3");
        calcSumEle.addChildElement("b").addTextNode("5");
                                                                                                                                                                         
        return soapRequest;
    }
                                                                                                                                                                     

    public static SOAPMessage sendMessage ( String wsdlURLString, String serviceQName,
    		String serviceProviderServiceName, String serviceProviderPortName,SOAPMessage soapRequest,MessageFactory factory) 
    				throws MalformedURLException,SOAPException
    {
        //把SOAPMessage转为Source类型
        DOMSource requestMsg =  new DOMSource(soapRequest.getSOAPPart());
                                                                                                                                                                                                                                                                  
        URL wsdlURL = new URL(wsdlURLString);
                                                                                                                                                                                                                                                                  
        //构造一个Service对象
        QName serviceProvider = new QName(serviceQName,serviceProviderServiceName);
        QName portName        = new QName(serviceQName,serviceProviderPortName);
        Service service  = Service.create(wsdlURL, serviceProvider);
                                                                                                                                                                                                                                                                  
        //利用Service对象来发送(Dispatch) Source类型的SOAPMessage到指定的Port上
        Dispatch<DOMSource> dispatcher  = service.createDispatch(portName, DOMSource.class, Mode.MESSAGE);   //PAYLOAD只发SOAP中的body,MESSAGE发整个SOAP
        //获得响应消息
        
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "POST");
        
        DOMSource respMsg = dispatcher.invoke(requestMsg);
        SOAPMessage soapResponse = factory.createMessage();
        soapResponse.getSOAPPart().setContent(respMsg);
                                                                                                                                                                                                                                                                  
        return soapResponse;
                                                                                                                                                                                                                                                                                  
    }                                                                                                                                                               
                                                                                                                                                                     
    public static void main(String [] args) throws Exception {
                                                                                                                                                                         
        String wsdlURLStringForCalcPlus = "http://127.0.0.1:8080/J_CXF_Spring/ws/calcPlus?wsdl";//WebServer启动
        //String wsdlURLStringForCalcPlus = "http://localhost:9000/calcPlus?wsdl "; //本地启动有问题
        String serviceQName            =  "http://ws_provider/"; //对应包名
        String serviceProviderStringForCalcPlus = "CalcPlusServiceProviderService";
        String servicePortStringForCalcPlus = "CalcPlusServiceProviderPort";
                                                                                                                                                                         
        //构造要发送的Soap消息内容并且转为Source类型
        //从MessageFactory 构造一个要发送的Soap消息
        MessageFactory factory = MessageFactory.newInstance();
                                                                                                                                                                         
        SOAPMessage soapRequest= buildMessageForAdd(factory);
        System.out.println("发送的消息为:");
        soapRequest.writeTo(System.out);
        System.out.println();
                                                                                                                                                                         
                                                                                                                                                                         
                                                                                                                                                                         
        SOAPMessage soapResponse = sendMessage( wsdlURLStringForCalcPlus,serviceQName,serviceProviderStringForCalcPlus,servicePortStringForCalcPlus,
        		soapRequest, factory);
        System.out.println("响应的消息为：");
        soapResponse.writeTo(System.out);
                                                                                                                                                                         
                                                                                                                                                                         
        }
}