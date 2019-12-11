package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableConfigurationProperties({ConfigBean.class})//可有可无
public class ConfigController {
    @Autowired
    ConfigBean configBean;

    @Autowired
    MyProps myProps;
    
    @RequestMapping(value = "/showConfig") //server.context-path=/J_SpringBoot
    public String miya(){
    	System.out.println(myProps.getListProp1());
        return  configBean.getName()+" >>>>"+ configBean.getAge();
    }
}