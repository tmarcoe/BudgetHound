package com.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Category;
import com.entity.SubCategory;
import com.entity.Withdrawal;
import com.service.CategoryService;
import com.service.SubCategoryService;
import com.service.WithdrawalService;

@Controller
public class WithdrawalController {
	
	@Autowired
	WithdrawalService withdrawalService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	SubCategoryService subCategoryService;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("/createtransaction")
	public String createTransaction(Model model) {
		List<Category> catList = categoryService.retrieveRawList();
		List<SubCategory> subList = subCategoryService.retrieveRawList();
		model.addAttribute("subList", subList);
		model.addAttribute("catList", catList);
		model.addAttribute("withdrawal", new Withdrawal());
		
		return "createTransactionJsp";
	}
	
	@RequestMapping("/edittrans")
	public String editTransaction(@ModelAttribute("entryId") int entryId, Model model) {
		
		
		model.addAttribute("withdrawal", withdrawalService.retrieve(entryId));
		
		return "editTransactionJsp";
	}
	

}
