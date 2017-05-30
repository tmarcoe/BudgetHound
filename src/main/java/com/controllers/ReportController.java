package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.DepositService;
import com.service.WithdrawalService;

@Controller
public class ReportController {
	
	@Autowired
	WithdrawalService withdrawalService;
	
	@Autowired
	DepositService depositService;
	
	@RequestMapping("/piechart")
	public String getPieChart(@ModelAttribute("month") int month, @ModelAttribute("year") int year, Model model) {
		
		model.addAttribute("totalWithdrawals", withdrawalService.totalWithdrawals(month, year));
		model.addAttribute("totalDeposits", depositService.totalDeposits(month, year));
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		
		return "pieChartJsp";
	}
	@RequestMapping("/monthandyear")
	public String getMonthAndYear() {
		
		return "monthAndYearJsp";
	}

}
