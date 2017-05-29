package com.dao;

import java.util.List;

import com.entity.Category;

public interface ICategoryDao {
	
	public void create(Category category);
	public void update(Category category);
	public List<Category> retrieveList();
	public Category retreive(String cat);
	public void delete(Category category);
}
