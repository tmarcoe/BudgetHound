package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CategoriesDao;
import com.entity.Categories;

import interfaces.ICategories;

@Service
public class CategoriesService implements ICategories {

	@Autowired
	private CategoriesDao categoriesDao;
	
	@Override
	public void create(Categories categories) {
		categoriesDao.create(categories);
	}

	@Override
	public Categories retrieve(int entry_id) {
		return categoriesDao.retrieve(entry_id);
	}

	@Override
	public void update(Categories categories) {
		categoriesDao.update(categories);
	}

	@Override
	public void delete(Categories categories) {
		categoriesDao.delete(categories);
	}

}
