package kubernetes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //刷新配置
public class ConfigController {
	
	@Autowired
	private ConfigBean configBean;
	
	
	@Value("${myprop.name:default_val}")
	private String name; 
	
	@Value("${greeting.message:message_val}")
	private String  greeting;
	
	//http://localhost:8081/showConfig
	@RequestMapping(value = "/showConfig")
	public String showConfig()
	{
		String res="name="+name+",configBean.name="+configBean.getName()+",greeting="+greeting;
		System.out.println(res);
		return name;
	}
}
