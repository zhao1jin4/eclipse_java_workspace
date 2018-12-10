package freemarker;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// 没发现有什么用 
/*
@EnableWebMvc
public class Application implements WebMvcConfigurer {  
    public void addCorsMappings(CorsRegistry registry) {//允许跨域访问  
        registry.addMapping("/**")  
                .allowCredentials(true)  
                .allowedHeaders("*")  
                .allowedOrigins("*")  
                .allowedMethods("*");  
  
    }  
}
*/