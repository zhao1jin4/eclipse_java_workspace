package freemarker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class FreeController {
 
	@RequestMapping("free")  // http://127.0.0.1:8081/free
	public ModelAndView sayFree() {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("free");
		
		mv.getModelMap().put("user", "li123");
		
		List<UserVO> list=new ArrayList<>();
		
		UserVO user=new UserVO();
		user.setId(32);
		user.setUsername("李");
		user.setBirthday(new Date());
		
		list.add( user); 
		
		
		
		UserVO user2=new UserVO();
		user2.setId(322);
		user2.setUsername("李2");
		user2.setBirthday(new Date());
		
		list.add( user2); 
		
		mv.getModelMap().put("allUser", list);
		return mv;
	} 
	 
	
}
