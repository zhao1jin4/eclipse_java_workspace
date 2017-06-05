package spring_jsp.annotation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring_jsp.annotation.form.UserDetails;

@RestController
//也可以用 两个组合
//@Controller
//@ResponseBody
public class  RestControllerDemo 
{
  
  @RequestMapping(value="/resetController", method=RequestMethod.GET,
		  	produces={"application/xml", "application/json"})
  @ResponseStatus(HttpStatus.OK)
  public UserDetails getUser()
  {
    UserDetails userDetails = new UserDetails();
    userDetails.setUserName("Krishna");
    userDetails.setEmailId("krishna@gmail.com");
    return userDetails;
  }

  @RequestMapping(value="/resetControllerText", method=RequestMethod.GET ,produces={"text/html"})
  @ResponseStatus(HttpStatus.OK)
  public String getUserHtml() {
    return "<h2>hello</h2>";
  }
}
