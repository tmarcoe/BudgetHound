package com.service;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;

import com.entity.Category;

public interface ICategoryService {
	void create(Category category);
	void update(Category category);
	PagedListHolder<Category> retrieveList();
	List<Category> retrieveRawList();
	Category retrieve(String cat);
	void delete(Category cat);
}
