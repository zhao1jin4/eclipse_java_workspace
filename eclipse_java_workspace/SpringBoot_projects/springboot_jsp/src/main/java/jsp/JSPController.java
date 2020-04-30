package jsp;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class JSPController {
	 /*
   
spring.mvc.view.suffix=.jsp
spring.mvc.view.prefix=/WEB-INF/jsp/ 
 
<packaging>war</packaging>    要用tomcat启动才行

 <dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>jstl</artifactId>
</dependency> 

项目中最好只有一个类是  extends SpringBootServletInitializer 


	 */
	
	
	// http://localhost:8081/J_SpringBoot/jsp
	
	//首页找 public/index.html
	// 当404错误时自动到  public/error/404.html,或者定义  /error
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/jsp") 
	public ModelAndView jsp(HttpServletRequest request){

	    Locale locale = RequestContextUtils.getLocale(request);
        System.out.println("Locale:" + locale.getLanguage());

//        String msg1 = this.messageSource.getMessage("try", null, Locale.CHINA);
        String msg1 = this.messageSource.getMessage("try", null, Locale.CHINESE); 
        System.out.println("Msg:" + msg1);
        
		ModelAndView mv=new ModelAndView();
		mv.setViewName("index");//jar 启动就404,war启就OK
		mv.getModelMap().put("msgFromRedis", "testJSP");
		return mv;
	}
	
}