package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.dao.SubCategoryDao;
import com.entity.SubCategory;

@Service
public class SubCategoryService implements ISubCategoryService {
	
	@Autowired
	private SubCategoryDao subCategoryDao;

	@Override
	public void create(SubCategory category) {
		subCategoryDao.create(category);
	}

	@Override
	public void update(SubCategory category) {
		subCategoryDao.update(category);
	}

	@Override
	public PagedListHolder<SubCategory> retrieveList() {
		return new PagedListHolder<SubCategory>(subCategoryDao.retrieveList());
	}

	@Override
	public SubCategory retrieve(String cat) {
		return subCategoryDao.retreive(cat);
	}

	@Override
	public void delete(SubCategory cat) {
		subCategoryDao.delete(cat);
	}

	@Override
	public List<SubCategory> retrieveRawList() {
		return subCategoryDao.retrieveList();
	}

}
