package springboot_shiro.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping
public class PageInitController {

	@RequestMapping("test")
	public String test()
	{
		return "test";
	}
	@RequestMapping("main")
	public String main(HttpServletRequest req)
	{
//		req.getSession().getAttribute("")
		return "main";
	}
}
