package spring_jsp.annotation;
import org.springframework.beans.factory.annotation.Autowired;   
import org.springframework.stereotype.Controller;   
import org.springframework.ui.ModelMap;   
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;   
import org.springframework.web.bind.annotation.RequestMethod;   
import org.springframework.web.bind.annotation.RequestParam;    

import spring_jsp.annotation.form.Account;
import spring_jsp.annotation.form.AccountService;
  
@Controller  
@RequestMapping(value = "/testform")   
public class ProfileController {   
    @Autowired  
    private AccountService accountService;   
    @RequestMapping(value = "/profile",method = RequestMethod.GET)   
    public String profile( @RequestParam(value="id",required=false) int id,
    				ModelMap model) 
    {   
    	
    	Account account = accountService.read(id);   
        model.addAttribute("account", account);
       
        //----------------------
    	//@ModelAttribute("account")Account account
//        System.out.println(account.getUsername());
        return "company_annotation/profile";   
    } 
    @RequestMapping(value = "/okEmail",method = RequestMethod.GET)   
    public String okEmail( @RequestParam(value="email",required=false) String email,
    				ModelMap model) {   
    	
        model.addAttribute("email", email);
        //----------------------
        return "company_annotation/profile";   
    }   
}  