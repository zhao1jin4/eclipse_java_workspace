package spring_session_hazelcast;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SpringSessionController {
	
	@RequestMapping("/hazelSession")
	// http://localhost:8080/springboot_springSessionHazel/hazelSession?action=set&msg=123 
	// http://localhost:8080/springboot_springSessionHazel/hazelSession?action=get
	@ResponseBody
	public String index(Model model,HttpServletRequest request,String action,String msg){
		HttpSession session=request.getSession();
		if ("set".equals(action)){
			session.setAttribute("msg", msg);
		}else if ("get".equals(action)){
			String message=(String)session.getAttribute("msg");
			return message;
		}
		return "index";
	}
	
}