package kubernetes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; 
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class MainKubernetesApp {

	@Autowired
  private DiscoveryClient discoveryClient;

  @Autowired
  private  RestTemplate restTemplate;

  @Bean
  public RestTemplate restTemplate()
  {
    return new RestTemplate();
  } 

  //http://localhost:8081/showConfig
    
  //请求  http://localhost:8081/show-tomcat-svc
  @GetMapping("/show-tomcat-svc")
  public List<String> showService() {
      List<ServiceInstance> list = discoveryClient.getInstances("tomcat-svc");
      List<String> services = new ArrayList<>();
      if (list != null && list.size() > 0 ) {
          list.forEach(serviceInstance -> {
              services.add(serviceInstance.getUri().toString());
          });
      }
      return services;
  }
   //请求  http://localhost:8081/req-tomcat-svc 
  @GetMapping("/req-tomcat-svc")
  public String requestService() {
    //istio 示例
    return restTemplate.getForObject("http://tomcat-svc:8080/index.html",String.class);
  }

  //mvn spring-boot:run  ,vscode 可以点这块的 Run|Debug
  //全部未成功???
	public static void main(String[] args) {
		 SpringApplication.run(MainKubernetesApp.class);
	}
/*

---Dockerfile
#FROM tomcat:9.0.34-jdk11  #源码是从 FROM openjdk:11-jdk (/usr/local/openjdk-11版本是11.0.7,/usr/local/tomcat)
FROM openjdk:11-jdk
RUN mkdir -p /app  /tmp/logs/
WORKDIR /app
COPY target/cloud-k8s.jar /app/
#ADD target/${JAR_FILE} /app/myservice.jar

VOLUME ["/tmp/logs/"]
EXPOSE 8081
CMD ["--spring.profiles.active=dev"]
ENTRYPOINT ["java","-jar","cloud-k8s.jar"]
---
docker build -t cloud-k8s:0.1 .

docker run  -p 8080:8081 -v ~/logs:/tmp/logs -d --name my-cloud-k8s  cloud-k8s:0.1 
  -p 8080:8081  			#-p 本机端口:docker端口
  -v ~/logs:/tmp/logs 	#-v 本机目录:docker目录
  

kubectl create namespace my-ns
名字不能有大写字母
kubectl create configmap my-cloud-config --from-file=application-dev.properties -n my-ns   
kubectl get  cm my-cloud-config  -o yaml -n my-ns 
kubectl describe  cm  my-cloud-config  -o yaml -n my-ns 
kubectl edit  cm  my-cloud-config -n my-ns 
	  
--vi my-cloud-config2.yml
apiVersion: v1
kind: ConfigMap
metadata:
  name: my-cloud-config2
  namespace: my-ns
data:
  myprop.name: prop_name_test
  
--
kubectl apply -f my-cloud-config2v.yml 

--vi my-cloud-config-dev.yml
apiVersion: v1
kind: ConfigMap 
metadata:
  name: my-cloud-config
  namespace: my-ns
data:
  application.yaml: |-
    spring:
      profiles: dev
    myprop:
      greeting: Say Hello to the Developers
      name: prop_val_dev
--
kubectl apply -f my-cloud-config-dev.yml 
        
 

*/

}
