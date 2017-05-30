package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.dao.CategoryDao;
import com.entity.Category;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public void create(Category category) {
		categoryDao.create(category);
	}

	@Override
	public void update(Category category) {
		categoryDao.update(category);
	}

	@Override
	public PagedListHolder<Category> retrieveList() {
		return new PagedListHolder<Category>(categoryDao.retrieveList());
	}

	@Override
	public Category retrieve(String cat) {
		return categoryDao.retreive(cat);
	}

	@Override
	public void delete(Category cat) {
		categoryDao.delete(cat);
	}

	@Override
	public List<Category> retrieveRawList() {
		return categoryDao.retrieveList();
	}

}
