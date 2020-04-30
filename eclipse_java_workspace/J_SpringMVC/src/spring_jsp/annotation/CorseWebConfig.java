package spring_jsp.annotation;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //ע�;͹ر�
//@EnableWebMvc
public class CorseWebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	/*Ҳ����ʹ��xml����
		<mvc:cors>
			 <mvc:mapping path="/cors/**"  
		        allowed-origins="http://localhost:8080, http://127.0.0.1:8080"  
		        allowed-methods="GET, POST"  
		        allowed-headers="content-type"
		        allow-credentials="false"  
		        max-age="123" />  
		   <!-- �����ö��mvc:mapping -->
		</mvc:cors>
       
        registry.addMapping("/cors/**")
            .allowedOrigins("http://127.0.0.1:8080","http://localhost:8080")
            .allowedMethods("GET", "POST")
            .allowedHeaders("content-type")
            //.exposedHeaders("header1", "header2")
            .allowCredentials(true).maxAge(3600);
             */
    }
   
    //��ʽ�� ʹ��spring��Filter
     @Bean
     public CorsFilter corseFilter()
     {
    	UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
    	CorsConfiguration config=new CorsConfiguration();
    	config.setAllowCredentials(true);
    	config.setAllowedHeaders(Arrays.asList("content-type"));
    	config.setAllowedMethods(Arrays.asList("OPTIONS","GET","POST"));
    	config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:8080","http://localhost:8080"));
    	//config.setMaxAge(maxAge);//
    	source.registerCorsConfiguration("/cors/**", config);
    	return new CorsFilter(source);//spring 4.2���¹���
		//��Ϊ�Ѿ���*.mvc��������,����url-mapping
    	//spring security�� Cors ��֧��
     }
  
}
 
