package kubernetes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication 
@RestController 
@EnableFeignClients 
public class MainKubernetesApp {


/* 
 
 本机PATH中有kubectl命令(有windows版本)，~/.kube/config 客户端文件配置正确
 #kubectl create namespace my-ns
 
 kubectl create cm  my-cloud-config  --from-literal=myprop.name=myk8s_val  -n  my-ns
 kubectl get cm -n my-ns	
 kubectl delete cm  my-cloud-config  -n  my-ns  

 kubectl create secret generic   my-cloud-secret  --from-literal=myprop.password=myk8s_pass -n  my-ns
kubectl get secret -n my-ns
 kubectl delete secret  my-cloud-secret  -n  my-ns  
 
docker build -t cloud-service:1.0 .
docker image save -o cloud-service-1.0.tar cloud-service:1.0  
docker load -i  cloud-service-1.0.tar  
 
 
 
 
 
kubectl create serviceaccount def-ns-admin -n default   #只管理default名称空间，使用rolebinding
kubectl create rolebinding def-ns-admin --clusterrole=admin --serviceaccount=default:def-ns-admin
#kubectl delete rolebinding def-ns-admin  
#
kubectl create clusterrolebinding def-cluster-admin --clusterrole=cluster-admin  --serviceaccount=default:def-ns-admin
#kubectl delete clusterrolebinding def-cluster-admin  
	
	
kubectl get secret | awk '/^def-ns-admin/{print $1}'
kubectl describe secret  def-ns-admin-token-xxx (也可用里面的token登录，测试成功)

kubectl get secret -n default -o jsonpath={.items[0].data.token} | base64 -d   是base64加密的，解密可登录
 
登录的token 使用做config命令的set-credentials
kubectl get secret -n default -o json 返回数组,找有 def-ns-admin-token-xxx 组中的 token(结尾有==是base64加密的，解密可登录 )

#jsonpath 文档 https://goessner.net/articles/JsonPath/  
kubectl get secret -n default 有 def-ns-admin-token-xxx
kubectl get secret  def-ns-admin-token-xxx -n default -o jsonpath={.data.token}  
还是复制到DEF_NS_AMDIN_TOKEN变量中吧
#DEF_NS_AMDIN_TOKEN=(kubectl get secret def-ns-admin-token-xxx -n default -o jsonpath={.data.token} | base64 -d)  

cd /etc/kubernetes/pki/

kubectl config set-cluster kubernetes --certificate-authority=./ca.crt --server="https://<api-server>:6443" --embed-certs=true --kubeconfig=/root/def-ns-admin.conf  #生成文件，起cluster名为kubernetes

kubectl config view --kubeconfig=/root/def-ns-admin.conf

kubectl config set-credentials def-ns-admin --kubeconfig=/root/def-ns-admin.conf --token=$DEF_NS_AMDIN_TOKEN ##前面的要base64解码
 #文件中增加了用户 def-ns-admin ，密码为xxx
 
kubectl config set-context def-ns-admin@kubernetes --cluster=kubernetes --user=def-ns-admin  --kubeconfig=/root/def-ns-admin.conf
#文件中context把user和cluster做关联

kubectl config use-context def-ns-admin@kubernetes --kubeconfig=/root/def-ns-admin.conf  #设置当前使用的context
就可以使用/root/def-ns-admin.conf文件登录了 (测试成功)

客户端使用格式
kubectl get pod  --kubeconfig=/C/app/tmp/vm_k8s_config 
 */
	
	
	
	
  //curl http://localhost:8081/showConfig ,使用config1.yml ，和 config2.yml  ,  测试成功 
  //修改config处动重启

	
  //	curl http://localhost:8081/showRemoteIp 
  //	 curl http://localhost:8081/showService
 // curl http://localhost:8081/showIp 
 // k8s pod 中ping myk8s-app 返回   myk8s-app.my-ns.svc.cluster.local
	
		
  
  //mvn spring-boot:run  ,vscode 可以点这块的 Run|Debug 
	public static void main(String[] args) {
		 SpringApplication.run(MainKubernetesApp.class);
	}
	

	@Bean
	public RestTemplate restTemplate()
	{
	  return new RestTemplate();
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
