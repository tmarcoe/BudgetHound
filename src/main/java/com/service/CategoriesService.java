package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
		if (categories.isProtect() == false) {
			categoriesDao.delete(categories);
		}
	}
	public List<String> retrieveSubCategories(int household_id, String parent) {
		return categoriesDao.retrieveSubCategories(household_id, parent);
	}
	public Categories retrieveCategoryByName(int household_id, String category) {
		return categoriesDao.retrieveCategoryByName(household_id, category);
	}

	public PagedListHolder<Categories> retrieveList(int household_id, String parent) {
		return new PagedListHolder<Categories>(categoriesDao.retrieveList(household_id, parent));
	}
	public boolean exists(int household_id, String category) {
		return categoriesDao.exists(household_id, category);
	}
	public boolean hasSubCategories(int household_id, String category) {
		return categoriesDao.hasSubCateories(household_id, category);
	}
	public List<Categories> retrieveByParent(int household_id, String parent) {
		return categoriesDao.retrieveByParent(household_id, parent);
	}

	public List<Categories> retrieveRawList(int household_id, String parent) {
		return categoriesDao.retrieveList(household_id, parent);
	}
	public void deleteByHouseholdId(int household_id) {
		categoriesDao.deleteByhouseholdId(household_id);
	}
	public void zeroCategories(int household_id) {
		categoriesDao.zeroCategories(household_id);
	}
}
