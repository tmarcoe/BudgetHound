package com.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.beans.factory.annotation.Autowired;
import com.service.CategoriesService;

public class HasSubCategories extends SimpleTagSupport {
	private int household_id;
	private String category;

	@Autowired
	CategoriesService categoriesService;
	
	public void setHousehold_id(int household_id) {
		this.household_id = household_id;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void doTag() throws JspException, IOException{
		if (categoriesService.hasSubCategories(household_id, category)) {
			getJspBody().invoke(null);
		}
	}

}
