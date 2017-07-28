package com.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Categories;
import com.entity.Household;
import com.service.CategoriesService;
import com.service.HouseholdService;
import com.service.RegisterService;

@Controller
@RequestMapping("/user")
public class CategoriesController {
	private final String pageLink = "/user/%s/categoriespaging";
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private HouseholdService householdService;
	
	@Autowired
	private RegisterService registerService;
	
	private PagedListHolder<Categories> categoriesList;
	
	@RequestMapping("/{parent}/listcategories")
	public String listCategories(@PathVariable("parent") String parent, Model model, Principal principal) {
		
		if (parent.length() == 0) {
			parent = "root";
		}
		
		Household household = householdService.retrieve(principal.getName());
		categoriesList = categoriesService.retrieveList(household.getHousehold_id(), parent);
		
		categoriesList.setPage(0);
		categoriesList.setPageSize(15);
		model.addAttribute("error", "");
		model.addAttribute("parent", parent);
		model.addAttribute("objectList", categoriesList);
		model.addAttribute("pagelink", String.format(pageLink, parent));
		
		return "listcategories";
	}
	
	@RequestMapping("/{parent}/createcategory")
	public String createCategory(@PathVariable("parent") String parent, @ModelAttribute("category") String category, Model model, Principal principal) {
		Household household = householdService.retrieve(principal.getName());
		
		if (categoriesService.exists(household.getHousehold_id(), category) == false) {
			Categories cat = new Categories(category, household.getHousehold_id(), parent, 0, false);	
			categoriesService.create(cat);
		}else{
			model.addAttribute("error", "Category name already used.");
		}
		categoriesList = categoriesService.retrieveList(household.getHousehold_id(), parent);
		
		model.addAttribute("parent", parent);
		model.addAttribute("objectList", categoriesList);
		model.addAttribute("pagelink", String.format(pageLink, parent));
		
		return String.format("redirect:/user/%s/listcategories", parent);
	}
	
	@RequestMapping("/{parent}/uponecat")
	public String upOneCategories(@PathVariable("parent") String parent, Model model, Principal principal) {
		Household household = householdService.retrieve(principal.getName());
		
		Categories par = categoriesService.retrieveCategoryByName(household.getHousehold_id(), parent);

		categoriesList = categoriesService.retrieveList(household.getHousehold_id(), par.getParent());
		
		model.addAttribute("parent", par.getParent());
		model.addAttribute("objectList", categoriesList);
		model.addAttribute("pagelink", String.format(pageLink, parent));
		
		return String.format("redirect:/user/%s/listcategories", par.getParent());
	}
	
	@RequestMapping("/{parent}/deletecategory")
	public String deleteCategory(@PathVariable("parent") String parent, @ModelAttribute("id") int id, Model model,Principal principal) {
		Household household = householdService.retrieve(principal.getName());
			
		Categories cat = categoriesService.retrieve(id);
		if (registerService.transactionsExistByCategory(household.getHousehold_id(), cat.getCategory())) {
			
			categoriesList = categoriesService.retrieveList(household.getHousehold_id(), parent);
			
			categoriesList.setPage(0);
			categoriesList.setPageSize(15);
			model.addAttribute("error", "You cannot delete a category while it has transactions");
			model.addAttribute("parent", parent);
			model.addAttribute("objectList", categoriesList);
			model.addAttribute("pagelink", String.format(pageLink, parent));
			
			return "listcategories";
		}else{
			registerService.removeTransactionsByCategory(household.getHousehold_id(), cat.getCategory());
			categoriesService.delete(cat);
		}
		
		return String.format("redirect:/user/%s/listcategories", parent);
	}
	

	@RequestMapping(value = "/{parent}/categoriespaging", method = RequestMethod.GET)
	public String handleCategoriesPaging(@PathVariable("parent") String parent, @ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			categoriesList.nextPage();
		} else if ("prev".equals(page)) {
			categoriesList.previousPage();
		} else if (pgNum != -1) {
			categoriesList.setPage(pgNum);
		}
		model.addAttribute("parent", parent);
		model.addAttribute("objectList", categoriesList);
		model.addAttribute("pagelink", String.format(pageLink, parent));

		return "listcategories";
	}

	/**************************************************************************************************************************************
	 * Used for both detecting a number, and converting to a number. If this
	 * routine returns a -1, the input parameter was not a number.
	 * 
	 **************************************************************************************************************************************/

	private int isInteger(String s) {
		int retInt;
		try {
			retInt = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}
		// only got here if we didn't return false
		return retInt;
	}
	

}
