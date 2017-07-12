package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.HouseholdDao;
import com.entity.Household;

import interfaces.IHousehold;

@Service
public class HouseholdService implements IHousehold {

	@Autowired
	private HouseholdDao householdDao;
	
	@Override
	public void create(Household household) {
		householdDao.create(household);
	}

	@Override
	public Household retrieve(int entry_id) {
		return householdDao.retrieve(entry_id);
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
