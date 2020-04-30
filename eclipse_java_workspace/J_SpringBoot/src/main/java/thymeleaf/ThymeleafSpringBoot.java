package thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;


@SpringBootApplication

@ServletComponentScan (basePackages= {"jsp"}) //就可以自动把@WebServlet,@WebListener,@WebFilter 等servlet自动注册 

public class ThymeleafSpringBoot {   
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ThymeleafSpringBoot.class, args);
        ThymeleafAutoConfiguration auto;
    }
  /*  
  //--配置*.mvc ,不能加载.js,.jpg,.html ??? 去了这段就正常了，好像.mvc可以忽略一样
    @Bean
	public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
		bean.addUrlMappings("*.mvc");
		return bean;
	}
    */
}