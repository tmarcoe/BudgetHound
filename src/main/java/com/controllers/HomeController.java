package com.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Household;
import com.service.HouseholdService;

@Controller
public class HomeController {
	
	@Autowired
	HouseholdService householdService;
	
	@RequestMapping(value={"/", })
	public String showRoot() {
		return "redirect:/public/home";
	}
	
	@RequestMapping("/public/home")
	public String showHome() {
		
		return "home";
	}

	@RequestMapping("/public/signup")
	public String signUp(Model model) {
		
		model.addAttribute("household", new Household());
		
		return "signup";
	}
	
	@RequestMapping("/public/createhousehold")
	public String createHousehold(@Valid @ModelAttribute("household") Household household, BindingResult result) {
		if (result.hasErrors()) {
			return "signup";
		}
		if (householdService.exists(household.getUsername()) == true) {
			result.rejectValue("username", "Duplicate.household.username");
			return "signup";
		}
		householdService.create(household);
		
		return "redirect:/public/home";
		
	}
	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
}
