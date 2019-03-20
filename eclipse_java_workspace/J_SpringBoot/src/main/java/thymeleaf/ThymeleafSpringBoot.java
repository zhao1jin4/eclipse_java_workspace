package thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication

@ServletComponentScan (basePackages= {"jsp"}) //就可以自动把@WebServlet,@WebListener,@WebFilter 等servlet自动注册 

public class ThymeleafSpringBoot {   
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ThymeleafSpringBoot.class, args);
        ThymeleafAutoConfiguration auto;
    }
}