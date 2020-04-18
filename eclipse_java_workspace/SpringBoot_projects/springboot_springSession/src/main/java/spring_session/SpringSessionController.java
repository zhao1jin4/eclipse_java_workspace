package spring_session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringSessionController {
	
	
	@RequestMapping("/redisSession")
	// http://localhost:8080/springboot_springSession/redisSession?action=set&msg=123 
	// http://localhost:8080/springboot_springSession/redisSession?action=get
	public String index(Model model,HttpServletRequest request,String action,String msg){
		HttpSession session=request.getSession();
		if ("set".equals(action)){
			session.setAttribute("msg", msg);
		}else if ("get".equals(action)){
			String message=(String)session.getAttribute("msg");
			model.addAttribute("msgFromRedis",message);
		}
		return "index";//找不到先不管？？？
	}
	
}