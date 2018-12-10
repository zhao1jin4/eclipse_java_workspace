package spring_jsp.annotation;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;   
import org.springframework.stereotype.Controller;   
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;   
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;   
import org.springframework.web.bind.annotation.RequestMapping;   
import org.springframework.web.bind.annotation.RequestMethod;   
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import spring_jsp.annotation.form.Account;
import spring_jsp.annotation.form.AccountService;
import spring_jsp.annotation.form.EmailForm;
@Controller  
@RequestMapping(value = "/testform")   
public class LoginController {   
  
    @Autowired  
    private AccountService accountService;   

    private Map<Integer,String> getSkills()
    {
    	Map<Integer,String> allSkills=new HashMap<>();
    	allSkills.put(1001, "JAVA");
    	allSkills.put(1002, "c语言");
    	allSkills.put(1003, "英语");
    	allSkills.put(1004, "汉语"); 
    	return allSkills;
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)   
    public String initForm(Model model)//ModelMap 和 Model都    OK
    {
    	model.addAttribute("allSkills",getSkills());
    	
        model.addAttribute("accountForm", new Account());   //对一个页面两个表单,双两个Form都要加
        model.addAttribute("emailSendForm", new EmailForm());   
        // 直接跳转到登录页面   
        return "company_annotation/login";   
    }
    
    //@Valid 要打开  <mvc:annotation-driven/> 只可对表单提交式  JSON提交不行的,只认ValidationMessages.properties国际化文件
    @RequestMapping(value = "/submitLogin",method = RequestMethod.POST)   
    public String login(@Valid @ModelAttribute("accountForm") Account account  ,BindingResult result,Model model,HttpServletRequest request )
    		//表单对应的Bean属性最好不要用char类型,用String代替
//    		,RedirectAttributes redirectAttributes)//SpringMVC-3.1 only????
    {   
    	model.addAttribute("allSkills",getSkills());//页面不能为空
    	model.addAttribute("emailSendForm", new EmailForm()); //对一个页面两个表单
    	if(result.hasErrors())
    	{
    		for(ObjectError err:result.getAllErrors())
    		{
    			System.out.println(err.getObjectName()+"=="+err.getDefaultMessage());
    		}
    		
    		//代码中的国际化，也可 @Autowired private MessageSource messageSource;
//			ServletContext servletContext=request.getServletContext();
//			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);//web.xml中要必须有ContexLoaderListener注册过
//			Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);  
//		    String title = applicationContext.getMessage("title",new Object[] {"一","二"}, locale);  
//		    System.out.println("代码  国际化  title为:"+title);
		    
    		return "company_annotation/login";
    	}
    	
    
    	Account acc = accountService.read(account.getUsername(), account.getPassword());   
        if (acc != null)
        {
        	//redirectAttributes.addFlashAttribute("account", account);
        	//RedirectAttributes的目标Controller方法要有@ModelAttribute("myTestredirectAttr")/????
            return "redirect:/testform/profile.mvc?id=" + acc.getId(); 
        } else {
        	result.rejectValue("username", "username_error","默认值_用户名或者密码不存在");//读国际化
            return "company_annotation/login";   
        }   
    }
    
    @RequestMapping(value = "/emailSend")
    public String emailSend(@Valid @ModelAttribute("emailSendForm") EmailForm emailForm  ,BindingResult result,Model model,HttpServletRequest request)
	{   
    	model.addAttribute("allSkills",getSkills());
    	model.addAttribute("accountForm", new Account()); //对一个页面两个表单
		if(result.hasErrors())
		{
			//代码中的国际化方法,web.xml必须有ContexLoaderListener注册过
//			ServletContext servletContext=request.getServletContext();
//			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
//			Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);  
//		    String title = applicationContext.getMessage("title",null, locale);  
//		    System.out.println("代码  国际化  title为:"+title);
		    
			result.addError(new FieldError("emailSendForm","email","代码中验证表单表单的错误"));
			return "company_annotation/login";
		}	   
	
		return "redirect:/testform/okEmail.mvc?email="+emailForm.getEmail();
	}
    
    
	@ModelAttribute("monthsNames")//可在页面中任何位置引用 ${monthsNames}
	public  Map<Integer,String>  getMonthsNames()
	{
		Map<Integer,String>  months = new HashMap<> ();
		final DateFormatSymbols dateSymbols = new DateFormatSymbols(Locale.getDefault());
		final String[] symbols = dateSymbols.getMonths();
		for (int i = 1; i < symbols.length; i++)
		{
			months.put(i,symbols[i - 1]);
		}
		return months;
	}

    
}  