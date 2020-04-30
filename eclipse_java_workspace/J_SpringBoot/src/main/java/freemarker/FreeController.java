package freemarker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

//@CrossOrigin(origins = "http://192.168.1.97:8080", maxAge = 3600)//允许跨域访问 
@Controller
public class FreeController {
 
	@Autowired
	private MessageSource messageSource;
	
	
	@RequestMapping("/free")  // http://127.0.0.1:8081/J_SpringBoot/free 测试OK
	//*.mvc不行???
	public ModelAndView sayFree(HttpServletRequest request) {
		
/*		
 * <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-freemarker</artifactId>
		</dependency>
		
		spring.freemarker.enabled=true
		
		try {
			URL url=new URL("http://www.baidu.com");
			URLConnection conn=url.openConnection();
			InputStream input=conn.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(input));
			String line=null;
			while((  line=reader.readLine())!=null)
			{ 
				System.out.println(line);
			}
			reader.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}*/
		
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
		
		
		 Locale locale = RequestContextUtils.getLocale(request);
        System.out.println("Locale:" + locale.getLanguage());

//	    String msg1 = this.messageSource.getMessage("try", null, Locale.CHINA);
        String msg1 = this.messageSource.getMessage("try", null, Locale.CHINESE); 
        System.out.println("Msg:" + msg1);
        
		return mv;
	} 
	
	
	@RequestMapping("json")  // http://127.0.0.1:8081/json  
	@ResponseBody  //返回jSON
	public UserVO json() {
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
		return user;
	}
	
	@RequestMapping(value="reqJson",method= {RequestMethod.POST})  // http://127.0.0.1:8081/reqJson  
	@ResponseBody  
	public UserVO reqJson(@RequestBody UserVO req) { //请求JSON
		System.out.println("收到请求为"+req);
		return json();
	}
	//Content-Type=application/json
	//{"username":"李req","id":32,"birthday":"2018-06-30T08:21:39.956+0000"}
}
