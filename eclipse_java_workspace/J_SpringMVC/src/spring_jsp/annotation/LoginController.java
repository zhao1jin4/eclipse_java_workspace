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
    	allSkills.put(1002, "c����");
    	allSkills.put(1003, "Ӣ��");
    	allSkills.put(1004, "����"); 
    	return allSkills;
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)   
    public String initForm(Model model)//ModelMap �� Model��    OK
    {
    	model.addAttribute("allSkills",getSkills());
    	
        model.addAttribute("accountForm", new Account());   //��һ��ҳ��������,˫����Form��Ҫ��
        model.addAttribute("emailSendForm", new EmailForm());   
        // ֱ����ת����¼ҳ��   
        return "company_annotation/login";   
    }
    
    //@Valid Ҫ��  <mvc:annotation-driven/> ֻ�ɶԱ��ύʽ  JSON�ύ���е�,ֻ��ValidationMessages.properties���ʻ��ļ�
    @RequestMapping(value = "/submitLogin",method = RequestMethod.POST)   
    public String login(@Valid @ModelAttribute("accountForm") Account account  ,BindingResult result,Model model,HttpServletRequest request )
    		//����Ӧ��Bean������ò�Ҫ��char����,��String����
//    		,RedirectAttributes redirectAttributes)//SpringMVC-3.1 only????
    {   
    	model.addAttribute("allSkills",getSkills());//ҳ�治��Ϊ��
    	model.addAttribute("emailSendForm", new EmailForm()); //��һ��ҳ��������
    	if(result.hasErrors())
    	{
    		for(ObjectError err:result.getAllErrors())
    		{
    			System.out.println(err.getObjectName()+"=="+err.getDefaultMessage());
    		}
    		
    		//�����еĹ��ʻ���Ҳ�� @Autowired private MessageSource messageSource;
//			ServletContext servletContext=request.getServletContext();
//			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);//web.xml��Ҫ������ContexLoaderListenerע���
//			Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);  
//		    String title = applicationContext.getMessage("title",new Object[] {"һ","��"}, locale);  
//		    System.out.println("����  ���ʻ�  titleΪ:"+title);
		    
    		return "company_annotation/login";
    	}
    	
    
    	Account acc = accountService.read(account.getUsername(), account.getPassword());   
        if (acc != null)
        {
        	//redirectAttributes.addFlashAttribute("account", account);
        	//RedirectAttributes��Ŀ��Controller����Ҫ��@ModelAttribute("myTestredirectAttr")/????
            return "redirect:/testform/profile.mvc?id=" + acc.getId(); 
        } else {
        	result.rejectValue("username", "username_error","Ĭ��ֵ_�û����������벻����");//�����ʻ�
            return "company_annotation/login";   
        }   
    }
    
    @RequestMapping(value = "/emailSend")
    public String emailSend(@Valid @ModelAttribute("emailSendForm") EmailForm emailForm  ,BindingResult result,Model model,HttpServletRequest request)
	{   
    	model.addAttribute("allSkills",getSkills());
    	model.addAttribute("accountForm", new Account()); //��һ��ҳ��������
		if(result.hasErrors())
		{
			//�����еĹ��ʻ�����,web.xml������ContexLoaderListenerע���
//			ServletContext servletContext=request.getServletContext();
//			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
//			Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);  
//		    String title = applicationContext.getMessage("title",null, locale);  
//		    System.out.println("����  ���ʻ�  titleΪ:"+title);
		    
			result.addError(new FieldError("emailSendForm","email","��������֤�����Ĵ���"));
			return "company_annotation/login";
		}	   
	
		return "redirect:/testform/okEmail.mvc?email="+emailForm.getEmail();
	}
    
    
	@ModelAttribute("monthsNames")//����ҳ�����κ�λ������ ${monthsNames}
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