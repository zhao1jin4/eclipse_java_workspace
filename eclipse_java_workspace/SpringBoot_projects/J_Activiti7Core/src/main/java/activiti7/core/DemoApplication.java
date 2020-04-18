package activiti7.core;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private ProcessRuntime processRuntime; //也可以不使用这个，用老的API也行
  //接口唯一的实现类ProcessRuntimeImpl 上有spring security的 @PreAuthorize("hasRole('ACTIVITI_USER')")

    @Autowired
    private SecurityUtil securityUtil;

    public static void main(String[] args) {
    	//从示例代码 https://github.com/Activiti/activiti-examples 拿的部分
        SpringApplication.run(DemoApplication.class, args);
    }

//    会自动建立表,但没有act_hi_*表？？
    @Override
    public void run(String... args) {
    	//要求先登录系统,即使用SpringSecurity的SecurityContextHolder.setContext(xx)
    	
    	//既然Activiti内部使用SpringSecurity为何要使用下面语句呢？为何不直接从SpringSecurity取呢？
        //#####org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username); //测试下来也可没有这个
//        securityUtil.logInAs("system");
       
        
    }
  

}
