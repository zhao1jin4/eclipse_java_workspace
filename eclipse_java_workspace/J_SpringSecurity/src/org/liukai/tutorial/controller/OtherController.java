package org.liukai.tutorial.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
 

@Controller
@RequestMapping("/other") 
public class OtherController 
{
	 
	@RequestMapping(value = "/dbError", method = RequestMethod.GET)
	public ModelAndView dbError() throws SQLException {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new SQLException("我的SQL exception");
	    return mv; 
	}
	
	@RequestMapping(value = "/runtimeExcep", method = RequestMethod.GET)
	public ModelAndView runtimeExcep() throws  Exception  {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new   RuntimeException("Runtime 错误");
	    return mv; 
	}
	@RequestMapping(value = "/serverError", method = RequestMethod.GET)
	public ModelAndView serverError() throws  Exception  {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new   Exception("Exception 系统错误");
	    return mv; 
	}
	
	 
	
}
