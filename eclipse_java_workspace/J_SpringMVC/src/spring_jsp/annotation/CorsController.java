package spring_jsp.annotation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import spring_jsp.annotation.form.Employee;

@CrossOrigin(maxAge = 3600)  
@Controller
@RequestMapping("/cors") 
public class CorsController 
{

    @CrossOrigin(value={"http://localhost:8080","http://127.0.0.1:8080"},
    		methods = {RequestMethod.POST,RequestMethod.GET},
    		allowedHeaders = {"content-type"}
    		)  
    
	//���� MappingJackson2HttpMessageConverter
	@RequestMapping(value="/reqRespJSON", produces="application/json")
    @ResponseBody
    public Employee reqRespJSON(@RequestBody Employee emp,HttpServletRequest request) {
		System.out.println("req ="+emp);
		emp.setFirst_name(emp.getFirst_name()+"_resp");
        return emp; 
    }
	
    @CrossOrigin(value={"http://localhost:8080","http://127.0.0.1:8080"},
    		methods = {RequestMethod.POST,RequestMethod.GET},
    		allowedHeaders = {"content-type"}
    		)   
	//���� MappingJackson2HttpMessageConverter
	@RequestMapping(value="/responseBodyJSON", produces="application/json")
    @ResponseBody
    public  Map<String, Object> responseBodyJSON(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "1231231");
        map.put("nullObj",null);//nullֵ�ɲ���ʾ��Ҳ����ʾΪ�մ������������ʹ����������Ϊnull
        map.put("reason", "ԭ��");//�������Ŀ���
        return map;
        //return "{status:'1231231',reason:'ԭ��'}";//�ַ�������֧��
    }
	 
}
