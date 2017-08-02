package com.controllers;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Household;
import com.entity.Role;
import com.service.CategoriesService;
import com.service.HouseholdService;
import com.service.RegisterService;
import com.service.RoleService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final String pageLink = "/housepaging";
	
	@Autowired
	private HouseholdService householdService;
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	CategoriesService categoriesService;
	
	@Autowired
	private RoleService roleService;
	
	PagedListHolder<Household> houseList;

	@RequestMapping("/shutdown")
	public String shutdown() {
		
		System.exit(0);
		
		return "redirect:/public/home";
	}
	
	@RequestMapping("/listusers")
	public String listUsers(Model model) {
		houseList = householdService.retrieveList();
		
		model.addAttribute("objectList", houseList);
		model.addAttribute("pagelink", pageLink);
		
		
		return "listusers";
	}
	
	@RequestMapping("/edituser")
	public String editUser(@ModelAttribute("household_id") int household_id, Model model) {
		String roleIndex = "";
		Household household = householdService.retrieve(household_id);
		for (Role r: household.getRoles()) {
			if (roleIndex.length() > 0) {
				roleIndex += ("," + String.valueOf(r.getId()));
			}else{
				roleIndex += String.valueOf(r.getId());
			}
		}

		model.addAttribute("roles", roleService.retrieveList());
		model.addAttribute("roleIndex", roleIndex);
		model.addAttribute("household", household);
		
		return "edituser";
	}
	
	@RequestMapping("/deleteuser")
	public String deleteUser(@ModelAttribute("household_id") int household_id) {
		householdService.delete(household_id);
		registerService.deleteByHouseholdId(household_id);
		categoriesService.deleteByHouseholdId(household_id);
		
		return "redirect:/admin/listusers";
	}
	
	@RequestMapping("/saveuser")
	public String saveUser(@ModelAttribute("household") Household household, BindingResult result) {


		if (household.getRoles() == null) {
			household.setRoles(new HashSet<Role>());
		}
		
		String[] roleNames = household.getRoleString().split(";");
		for (int i = 0 ; i < roleNames.length; i++) {
			household.getRoles().add(roleService.retrieve(roleNames[i]));
		}
		householdService.merge(household);
		
		return "redirect:/admin/listusers";
	}
	
	@RequestMapping(value = "/housepaging", method = RequestMethod.GET)
	public String handleHousePaging(@PathVariable("parent") String parent, @ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			houseList.nextPage();
		} else if ("prev".equals(page)) {
			houseList.previousPage();
		} else if (pgNum != -1) {
			houseList.setPage(pgNum);
		}


		model.addAttribute("objectList", houseList);
		model.addAttribute("pagelink", pageLink);

		return "listusers";
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
