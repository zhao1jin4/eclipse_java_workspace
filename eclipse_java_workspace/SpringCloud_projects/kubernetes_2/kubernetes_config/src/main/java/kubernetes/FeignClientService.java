package kubernetes;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//自已调用 自己的service，再回来

@FeignClient(value = "myk8s-app"
	,url = "http://myk8s-app:9000"  
	//k8s下的容器中ping myk8s-app 返回 myk8s-app.dev.svc.cluster.local  
//{service-name}.{namespace}.svc.{cluster}.local:{service-port}.
	)
public interface FeignClientService {

	 @GetMapping (value="/showIp")
	 String  feignK8sShowIp();

}
