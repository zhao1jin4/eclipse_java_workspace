package kubernetes;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ConfigController {
	
	@Autowired
	private ConfigBean configBean;
	
	
	@Value("${myprop.name:default_name}")
	private String name; 
	@Value("${myprop.password:default_pass}")
	private String password; 
	
	
	@Value("${greeting.message:default_msg}")
	private String  greeting;
	
	//http://localhost:8081/showConfig
	@RequestMapping(value = "/showConfig")
	public String showConfig()
	{
		String res="name="+name+",configBean.name="+configBean.getName()+",greeting="+greeting;
		System.out.println(res);
		
		String passwordRes="password="+password+",configBean.password="+configBean.getPassword();
		System.out.println(passwordRes);
		return name+"++"+password;
	}
	
	
	
//------------------------
	@Autowired
	private DiscoveryClient discoveryClient;
  
	@Autowired
	private FeignClientService feignClientService;
	
	@Autowired
	private  RestTemplate restTemplate; 


	  @GetMapping("/showIp") //discover server
	  @ResponseBody 
	  public String showIp() {
	      try{

	          Set<String> res=new HashSet<>();
	          Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	          InetAddress ip = null;
	          while (allNetInterfaces.hasMoreElements())
	          {
	              NetworkInterface netInterface =  allNetInterfaces.nextElement();
	              if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
	                  continue;
	              }
	              //System.out.println(netInterface.getName());
	              Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
	              while (addresses.hasMoreElements())
	              {
	                  ip =  addresses.nextElement();
	                  if (ip != null && ip instanceof Inet4Address)
	                  {
	                      res.add(ip.getHostAddress());
	                      System.out.printf("本机 网卡%s 的 IP是 %s %n" ,netInterface.getName(), ip.getHostAddress());
	                  }
	              }
	          }
	          return  res.toString();
	      }catch (Exception e)
	      {
	          e.printStackTrace();
	          return  e.getMessage();
	      }
	  }

	//feign测试 请求 curl http://localhost:8081/showRemoteIp 
	  @GetMapping("/showRemoteIp") //discover client
	  public Object feignRequest() {
		  try {
			  Object res=feignClientService.feignK8sShowIp();  
			  System.out.println(res.toString());
			  return res.toString();
		  }catch(Exception e)
		  {
			  e.printStackTrace();
			  return e.getMessage();
		   }
	  }
	  //请求 curl  http://localhost:8081/restRequest
	  @GetMapping("/restRequest") //discover client
	  public Object restRequest() {
	    return restTemplate.getForObject("http://myk8s-app:9000/showIp",String.class);
	  }
	  
	  /*
	   * 报
	   * Forbidden!Configured service account doesn't have access. Service account may have been revoked. endpoints "myk8s-app" is forbidden: User "system:serviceaccount:my-ns:default" cannot list resource "endpoints" in API group "" at the cluster scope.
	   * 要执行 kubectl create clusterrolebinding myglobal-cluster-admin --clusterrole=cluster-admin --group=system:serviceaccounts --namespace=my-ns
	   * 才有权限 
	   */
	  //请求  curl http://localhost:8081/showService
	  @GetMapping("/showService")
	  @ResponseBody
	  public List<String> showK8sService() {
	      List<ServiceInstance> list = discoveryClient.getInstances("myk8s-app");  
	      //是可以得到K8s 上service -> 多个pod的正确的IP，k8s上service类型可能为LoadBalance使用spring-cloud-gateway的lb://kubernetesApp 是可以的
	      List<String> services = new ArrayList<>();
	      if (list != null && list.size() > 0 ) {
	          list.forEach(serviceInstance -> {
	              services.add(serviceInstance.getUri().toString());
	          });
	      }
	      return services;
	  }

	 
	
	
}
