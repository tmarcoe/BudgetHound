package com.service;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.dao.RegisterDao;
import com.entity.Register;

import interfaces.IRegister;

@Service
public class RegisterService implements IRegister {

	@Autowired
	private RegisterDao registerDao;
	
	@Override
	public void create(Register register) {
		registerDao.create(register);
	}

	@Override
	public Register retrieve(int entry_id) {
		return registerDao.retrieve(entry_id);
	}
	
	public PagedListHolder<Register> retrieveList(int household_id, Date start, Date end, String parent) throws ParseException {
		return new PagedListHolder<Register>(registerDao.retieveList(household_id, start, start, parent));
	}

	@Override
	public void update(Register register) {
		registerDao.update(register);
	}

	@Override
	public void delete(Register register) {
		registerDao.delete(register);
	}
	
	public void totalTransaction(int household_id, String parent) {
		registerDao.totalTransactions(household_id, parent);
	}
	public double getEndingBalance(int household_id, String parent) {
		return registerDao.getEndingBalance(household_id, parent);
	}
	public double getExpenseByCategory(int household_id, String category) {
		
		return registerDao.getExpenseByCategory(household_id, category);
	}
}
