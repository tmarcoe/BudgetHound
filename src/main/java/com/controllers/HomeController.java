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

import com.entity.Withdrawal;
import com.service.WithdrawalService;

@Controller
public class HomeController {
	private final String pageLink = "/transPaging";
	
	@Autowired
	WithdrawalService withdrawalService;
	private PagedListHolder<Withdrawal> pagedList;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	

	
	@RequestMapping("/")
	public String showRoot(Model model) {
		
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String showHome(Model model) {
		pagedList =  withdrawalService.retrieveList();
		model.addAttribute("objectList", pagedList);
		model.addAttribute("pagelink", pageLink);
		
		return "homepagejsp";

	}
	@RequestMapping("/updatetrans")
	public String updateTransaction(@ModelAttribute("transactions") Withdrawal withdrawal, Model model) {
		
		withdrawalService.update(withdrawal);
		
		
		return "redirect:/home";
	}
	
	@RequestMapping("/savetrans")
	public String saveTransaction(@ModelAttribute("transactions") Withdrawal withdrawal, Model model) {
		withdrawal.setEntryDate(new Date());
		
		withdrawalService.create(withdrawal);
		

		
		return "redirect:/home";
	}

	
	@RequestMapping ("/transPaging")
	public String handleTransPaging(@ModelAttribute("page") String page, Model model) {
		if (pagedList == null) {
			return "redirect:/home";
		}
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

		return "homepagejsp";
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
