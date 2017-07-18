package com.controllers;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Categories;
import com.entity.Household;
import com.entity.Register;
import com.service.CategoriesService;
import com.service.HouseholdService;
import com.service.RegisterService;

@Controller
@RequestMapping("/user")
public class RegisterController {

	private final String pageLink = "/user/registerpaging";

	@Autowired
	private HouseholdService householdService;

	@Autowired
	private RegisterService registerService;
	
	@Autowired
	CategoriesService categoriesService;

	private PagedListHolder<Register> registerList;

	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping("/{parent}/createtrans")
	public String createTransaction(@PathVariable("parent") String parent, Model model, Principal principal) {
		
		Register register = new Register();
		register.setTrans_date(new Date());
		Household household = householdService.retrieve(principal.getName());
		
		model.addAttribute("parent", parent);
		model.addAttribute("catList", categoriesService.retrieveSubCategories(household.getHousehold_id(), parent));
		model.addAttribute("register", register);

		return "createtrans";
	}

	@RequestMapping("/{parent}/savetrans")
	public String saveTransaction(@PathVariable("parent") String parent, @ModelAttribute("register") Register register, Principal principal) {
		Household household = householdService.retrieve(principal.getName());
		register.setHousehold_id(household.getHousehold_id());
		double ending_balance = registerService.getEndingBalance(household.getHousehold_id(), parent);
		register.setRunning_balance((ending_balance + register.getDeposit()) - register.getWithdrawal());
		registerService.create(register);
		
		if (register.getCategory().length() > 0) {
			Categories cat = categoriesService.retrieveCategoryByName(household.getHousehold_id(), register.getCategory());
			cat.setAmount(registerService.getExpenseByCategory(household.getHousehold_id(), register.getCategory()));
			categoriesService.update(cat);
		}
		
		return String.format("redirect:/user/%s/listtrans", parent);
	}

	@RequestMapping("/{parent}/listtrans")
	public String listTransaction(@PathVariable("parent") String parent, Model model, Principal principal) throws ParseException {
		Household household = householdService.retrieve(principal.getName());
		
		
		registerList = registerService.retrieveList(household.getHousehold_id(), null, null, parent);

		model.addAttribute("parent", parent);
		model.addAttribute("objectList", registerList);
		model.addAttribute("pagelink", pageLink);

		return "listtrans";
	}
	
	@RequestMapping("/{parent}/edittrans")
	public String editTransaction(@PathVariable("parent") String parent, @ModelAttribute("entry_id") int entry_id, Model model, Principal principal) {
		
		Register register = registerService.retrieve(entry_id);
		Household household = householdService.retrieve(principal.getName());
		
		model.addAttribute("parent", parent);
		model.addAttribute("register", register);
		model.addAttribute("catList", categoriesService.retrieveSubCategories(household.getHousehold_id(), "root"));
		
		return "edittrans";
	}
	
	@RequestMapping("{parent}/updatetrans")
	public String updaateTransaction(@PathVariable("parent") String parent, @ModelAttribute("register") Register register) {
		registerService.update(register);
		registerService.totalTransaction(register.getHousehold_id(), parent);
		
		if (register.getCategory().length() > 0) {
			Categories cat = categoriesService.retrieveCategoryByName(register.getHousehold_id(),register.getCategory());
			cat.setAmount(registerService.getExpenseByCategory(register.getHousehold_id(), register.getCategory()));
			categoriesService.update(cat);
		}
		
		return String.format("redirect:/user/%s/listtrans", parent);
	}
	
	@RequestMapping("/{parent}/deletetrans")
	public String deleteTransaction(@PathVariable("parent") String parent, @ModelAttribute("entry_id") int entry_id) {
		Register reg = registerService.retrieve(entry_id);
		registerService.delete(reg);
		registerService.totalTransaction(reg.getHousehold_id(), parent);
		
		if (reg.getCategory().length() > 0) {
			Categories cat = categoriesService.retrieveCategoryByName(reg.getHousehold_id(),reg.getCategory());
			cat.setAmount(registerService.getExpenseByCategory(reg.getHousehold_id(), reg.getCategory()));
			categoriesService.update(cat);
		}
		
		return String.format("redirect:/user/%s/listtrans", parent);
	}

	@RequestMapping(value = "/registerpaging", method = RequestMethod.GET)
	public String handleRegisterPaging(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			registerList.nextPage();
		} else if ("prev".equals(page)) {
			registerList.previousPage();
		} else if (pgNum != -1) {
			registerList.setPage(pgNum);
		}
		model.addAttribute("objectList", registerList);
		model.addAttribute("pagelink", pageLink);

		return "listtrans";
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
