package com.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Deposit;
import com.service.DepositService;

@Controller
public class DepositController {
	private final String pageLink = "/depositPaging";
	
	@Autowired
	DepositService depositService;
	private PagedListHolder<Deposit> pagedList;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/deposits")
	public String listDeposits(Model model) {
		pagedList = depositService.retrieveList();
		
		model.addAttribute("objectList", pagedList);
		model.addAttribute("pagelink", pageLink);

		return "depositsJsp";
	}
	
	@RequestMapping("/adddeposit")
	public String addDepost(Model model) {
		
		model.addAttribute("deposit", new Deposit());
		
		return "addDepositJsp";
	}
	
	@RequestMapping("/savedeposit")
	public String saveDeposit(@ModelAttribute("deposit") Deposit deposit) {
		deposit.setEntryDate(new Date());
		depositService.create(deposit);
		
		return "redirect:/home";
	}
	@RequestMapping("/editdeposit")
	public String editDeposit(@ModelAttribute("entryId") int entryId, Model model) {
		Deposit deposit = depositService.retrieve(entryId);
		
		model.addAttribute("deposit", deposit);
		
		return "editDepositJsp";
		
	}
	
	@RequestMapping ("/updatedeposit")
	public String updateDeposit(@ModelAttribute("deposit") Deposit deposit) {
		
		depositService.update(deposit);
		
		return "redirect:/home";
	}
	
	@RequestMapping ("/depositPaging")
	public String handleDepositPaging(@ModelAttribute("page") String page, Model model) {
		
		int pgNum = isInteger(page);
		
		if ("next".equals(page)) {
			pagedList.nextPage();
		} else if ("prev".equals(page)) {
			pagedList.previousPage();
		} else if (pgNum != -1) {
			pagedList.setPage(pgNum);
		}
		
		model.addAttribute("objectList", pagedList);
		model.addAttribute("pagelink", pageLink);

		return "depositsJsp";
	}
	
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
