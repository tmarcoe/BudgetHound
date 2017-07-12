package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/public/home";
	}
	
	@RequestMapping("/public/home")
	public String showHome() {
		
		return "home";
	}

}
