package com.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.dao.RegisterDao;
import com.entity.Categories;
import com.entity.Register;

import interfaces.IRegister;

@Service
public class RegisterService implements IRegister {

	@Autowired
	private RegisterDao registerDao;
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Override
	public void create(Register register) {
		registerDao.create(register);
	}

	@Override
	public Register retrieve(int entry_id) {
		return registerDao.retrieve(entry_id);
	}
	
	public PagedListHolder<Register> retrieveList(int household_id, Date start, Date end, String parent) throws ParseException {
		List<Object[]> r = registerDao.retieveList(household_id, start, start, parent);
		List<Register> rl = new ArrayList<Register>();
		for (Object[] item : r) {
			rl.add((Register)item[0]);
		}

		return new PagedListHolder<Register>(rl);
	}
	public List<Categories> budgetBreakdown(int household_id, String parent) {
		double total = registerDao.getExpenseByParent(household_id, parent);
		List<Categories> catList = categoriesService.retrieveByParent(household_id, parent);
		
		for (Categories item : catList) {
			item.setAmount(registerDao.getExpenseByCategory(household_id, item.getCategory()));
			if (total > 0) {
				item.setPercentage((item.getAmount()/total) * 100);
			}else{
				item.setPercentage(0);
			}
		}
		
		return catList;
	}
	public void removeTransactionsByCategory(int household_id, String category) {
		registerDao.removeTransactionsByCategory(household_id, category);
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
		double deposits = registerDao.getTotalDeposits(household_id);
		double withdrawals = registerDao.getTotalWithdrawals(household_id, parent);
		
		return (deposits - withdrawals);
	}
	public double getExpenseByCategory(int household_id, String category) {
		
		return registerDao.getExpenseByCategory(household_id, category);
	}
	public void archiveBudget(int household_id, int month) {
		registerDao.archivePreviousMonth(household_id, month);
	}
}
