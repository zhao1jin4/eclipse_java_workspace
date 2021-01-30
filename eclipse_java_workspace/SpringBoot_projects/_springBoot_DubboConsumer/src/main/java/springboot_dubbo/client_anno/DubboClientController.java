package springboot_dubbo.client_anno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class  DubboClientController {
	@Autowired
	private DemoServiceComponent service;
	@RequestMapping(path="/client",produces = "text/plain;charset=UTF-8")
    public  String  client( ) {
        String hello = service.sayHello("world");
        System.out.println("result :" + hello);
        return hello;
    }
}
