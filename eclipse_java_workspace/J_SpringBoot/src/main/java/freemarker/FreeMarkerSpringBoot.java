package freemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication 
//@Import(Application.class)//没发现有什么用 
public class FreeMarkerSpringBoot { 
	//测试成功 
    public static void main(String[] args) throws Exception {
        SpringApplication.run(FreeMarkerSpringBoot.class, args);
    }
}