package jsp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
//--配置*.mvc 测试下来的这个没有效果  
@Configuration
@ComponentScan
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {
        pathMatchConfigurer.setUseSuffixPatternMatch(false);
        pathMatchConfigurer.setUseRegisteredSuffixPatternMatch(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
} 
*/