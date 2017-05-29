package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Category;
import com.service.CategoryService;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("/createcategory")
	public String createCategory(Model model) {
		
		model.addAttribute("category", new Category());
		
		return "createcategoryjsp";
	}
	
	@RequestMapping("/savecategory")
	public String saveCategory(@ModelAttribute("category") Category category) {
		categoryService.create(category);
		
		return "redirect:/home";
	}
	
	@RequestMapping("/deletecategory")
	public String deleteCategory(Model model) {
		
		return "deletecategoryjsp";
	}
	

}
