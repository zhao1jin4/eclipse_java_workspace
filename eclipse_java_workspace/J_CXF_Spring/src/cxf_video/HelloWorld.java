package cxf_video;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface HelloWorld {

   @WebResult(name="sayHiResult")  //在WSDL中显示参数名字,默认为return 
   String sayHi(@WebParam(name="speak")String text);//在WSDL中显示参数名字,默认是 arg0
   //也是发送/接收 SOAP消息的格式体(XML标签名)
   
}
