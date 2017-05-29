package com.service;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;

import com.entity.SubCategory;

public interface ISubCategoryService {
	void create(SubCategory category);
	void update(SubCategory category);
	PagedListHolder<SubCategory> retrieveList();
	List<SubCategory> retrieveRawList();
	SubCategory retrieve(String cat);
	void delete(SubCategory cat);

}
