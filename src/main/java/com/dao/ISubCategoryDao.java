package com.dao;

import java.util.List;

import com.entity.SubCategory;

public interface ISubCategoryDao {
	
	public void create(SubCategory category);
	public void update(SubCategory category);
	public List<SubCategory> retrieveList();
	public SubCategory retreive(String cat);
	public void delete(SubCategory category);
}
