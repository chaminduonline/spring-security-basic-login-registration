package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class AppController {

	private final UserService userService;

	@Autowired
	public AppController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value="/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/home")
	public String home(Model model){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user",user);
		model.addAttribute("users",this.userService.findAll());
		return "home";
	}
}
