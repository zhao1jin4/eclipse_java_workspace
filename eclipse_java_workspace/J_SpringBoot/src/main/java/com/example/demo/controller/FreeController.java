package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.UserMapper;
import com.example.demo.vo.UserVO;

@Controller
public class FreeController {
 
	@RequestMapping("free")
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
