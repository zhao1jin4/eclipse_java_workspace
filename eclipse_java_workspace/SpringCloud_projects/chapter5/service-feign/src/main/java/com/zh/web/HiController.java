package com.zh.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zh.service.SchedualServiceHi;

 
@RestController
public class HiController {

    @Autowired
    SchedualServiceHi schedualServiceHi;
    
//    @RequestMapping(value = "/hi",method = RequestMethod.GET)
//    public String sayHi(HttpServletRequest request,@RequestParam String name){
//         return schedualServiceHi.sayHiFromClientOne(name);
//    }
//    
    @RequestMapping(value = "/feignMVC",method = RequestMethod.GET)
    public String feignMVC(@RequestParam String owner){
        return schedualServiceHi.feignMVC(owner);
    }
 
	  @RequestMapping(value = "/feignMVCByte",method = RequestMethod.GET)
	  public ResponseEntity<byte[]> feignMVCByte(@RequestParam String owner){
	      return schedualServiceHi.feignMVCByte(owner);
	  }
    
    
}
