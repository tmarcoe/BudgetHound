package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.SubCategory;
import com.service.SubCategoryService;

@Controller
public class SubCategorycontroller {
	
	@Autowired
	SubCategoryService subCategoryService;
	
	@RequestMapping("newSubCategory")
	public String createSubCategory(Model model) {
		model.addAttribute("subCategory", new SubCategory());
		
		return "newSubCategoryjsp";
	}
	
	@RequestMapping("saveSubCategory")
	public String saveSubCategory(@ModelAttribute("subCategory") SubCategory subCategory) {
		subCategoryService.create(subCategory);
		
		return "redirect:/home";
	}

}
