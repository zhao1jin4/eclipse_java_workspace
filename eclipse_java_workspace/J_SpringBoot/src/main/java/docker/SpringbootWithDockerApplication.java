package docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
	
//同spring cloud , mvn package docker:bulid  连接端口 2375
//docker images
//docker run -p 8080:8080 -t forezp/springboot-with-docker
public class SpringbootWithDockerApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringbootWithDockerApplication.class, args);
    }
}