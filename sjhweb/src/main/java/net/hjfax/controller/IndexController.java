package net.hjfax.controller;

import net.hjfax.model.User;
import net.hjfax.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index.do")
	public String index() {
		System.out.println("-------------------");
		User user = new User();
		user.setGender(true);
		user.setName("张三");
		user.setUsername("zhangsan");
		userService.saveUser(user);
		
		return "index.jsp";
	}
}
