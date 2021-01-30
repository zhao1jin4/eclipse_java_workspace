package json_valid;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
//@EnableWebMvc //加了 就会自动覆盖了官方给出的/static, /public, META-INF/resources, /resources等存放静态资源的目录
public class MainJSONApplication {
    public static void main(String[] args) { 
    	// http://localhost:8081/J_SpringBoot/json_valid.html
    	//@Valid有效，日期格式有效
    	SpringApplication.run(MainJSONApplication.class, args);
    }
}
