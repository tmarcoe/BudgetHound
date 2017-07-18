package com.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Categories;
import com.entity.Household;
import com.service.CategoriesService;
import com.service.HouseholdService;

@Controller
@RequestMapping("/user")
public class CategoriesController {
	
	@Autowired
	CategoriesService categoriesService;
	
	@Autowired
	HouseholdService householdService;
	
	@RequestMapping("/{parent}/listcategories")
	public String listCategories(@PathVariable("parent") String parent, Model model, Principal principal) {
		
		if (parent.length() == 0) {
			parent = "root";
		}
		
		Household household = householdService.retrieve(principal.getName());
		List<Categories> catList = categoriesService.retrieveList(household.getHousehold_id(), parent);
		
		model.addAttribute("parent", parent);
		model.addAttribute("catList", catList);
		
		return "listcategories";
	}
	
	@RequestMapping("/{parent}/createcategory")
	public String createCategory(@PathVariable("parent") String parent, @ModelAttribute("category") String category, Model model, Principal principal) {
		Household household = householdService.retrieve(principal.getName());
		
		if (categoriesService.exists(household.getHousehold_id(), category) == false) {
			Categories cat = new Categories(category, household.getHousehold_id(), parent, 0, false);	
			categoriesService.create(cat);
		}else{
			model.addAttribute("error", "Duplicate category");
		}
		List<Categories> catList = categoriesService.retrieveList(household.getHousehold_id(), parent);
		
		model.addAttribute("parent", parent);
		model.addAttribute("catList", catList);

		
		return String.format("redirect:/user/%s/listcategories", parent);
	}
	
	@RequestMapping("/{parent}/uponelevel")
	public String upOneLevel(@PathVariable("parent") String parent, Model model, Principal principal) {
		Household household = householdService.retrieve(principal.getName());
		
		Categories par = categoriesService.retrieveCategoryByName(household.getHousehold_id(), parent);

		List<Categories> catList = categoriesService.retrieveList(household.getHousehold_id(), par.getParent());
		
		model.addAttribute("parent", par.getParent());
		model.addAttribute("catList", catList);

		
		return String.format("redirect:/user/%s/listcategories", par.getParent());
	}
	

}
