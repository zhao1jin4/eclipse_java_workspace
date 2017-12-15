package springmvc_hessian;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

@Controller
@RequestMapping("/")
public class HessianClientController {
	
	
	HttpRequestHandlerServlet x;
	
	@Autowired
	private BasicAPI hessianServiceClient;
		public BasicAPI getHessianServiceClient() {
		return hessianServiceClient;
	}
	public void setHessianServiceClient(BasicAPI hessianServiceClient) {
		this.hessianServiceClient = hessianServiceClient;
	}
	
	
	@RequestMapping("/hessianClient")
	public String testParamsStep1(ModelMap model) {
		
		MyRequest reqObj=new MyRequest(); //如传对象必须  implements  Serializable
		reqObj.setSystemId("123");
		reqObj.setSystemName("boss");
		hessianServiceClient.setObject(reqObj);
		
		System.out.println("====Hessian=====server changed SystemId: " + hessianServiceClient.getObject().getSystemId());
		
		return null;
	}

}
