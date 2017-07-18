package com.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dao.HouseholdDao;
import com.entity.Categories;
import com.entity.Household;
import com.entity.Role;

import interfaces.IHousehold;

@Service
public class HouseholdService implements IHousehold {
	private final String[] newCategories = {"Emergency Fund", "Housing", "Savings", "Utilities",  "Health Care", 
							  "Consumer Debt", "Food and Groceries", "Personal Care", "Entertainment"};
	private final String root = "root";
	@Autowired
	private HouseholdDao householdDao;
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void create(Household household) {
		household.setPassword(bCryptPasswordEncoder.encode(household.getPassword()));
		Set<Role> role = new HashSet<Role>();
		role.add(roleService.retrieve(1));
		household.setRoles(role);
		household.setEnabled(true);
		householdDao.create(household);
		for (int i=0; i < newCategories.length; i++) {
			categoriesService.create(new Categories(newCategories[i], household.getHousehold_id(), root, 0, true ));
		}
	}

	@Override
	public Household retrieve(int entry_id) {
		return householdDao.retrieve(entry_id);
	}

	public Household retrieve(String username) {
		
		return householdDao.retrieve(username);
	}


	@Override
	public void update(Household household) {
		householdDao.update(household);	
	}

	@Override
	public void delete(Household household) {
		householdDao.delete(household);	
	}

}
