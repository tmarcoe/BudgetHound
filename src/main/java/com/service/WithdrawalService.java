package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.dao.WithdrawalDao;
import com.entity.Category;
import com.entity.Withdrawal;
import com.helper.BudgetPercentages;

@Service
public class WithdrawalService implements IWithdrawalService {

	@Autowired
	WithdrawalDao withdrawalDao;
	
	@Override
	public void create(Withdrawal withdrawal) {
		withdrawalDao.create(withdrawal);
	}

	@Override
	public void update(Withdrawal withdrawal) {
		withdrawalDao.update(withdrawal);
	}

	@Override
	public Withdrawal retrieve(int entryId) {
		return withdrawalDao.retrieve(entryId);
	}

	@Override
	public PagedListHolder<Withdrawal> retrieveList() {
		return new PagedListHolder<Withdrawal>(withdrawalDao.retrieveList());
	}

	@Override
	public void delete(Withdrawal withdrawal) {
		withdrawalDao.delete(withdrawal);
	}
	
	public double getTotalByCategory(String category, int month, int year) {
		return withdrawalDao.getTotalByCategory(category, month, year);
	}
	
	public List<BudgetPercentages> getBudgetBreakdown(List<Category> categories, int month, int year) {
		double total = 0;
		double result = 0;
		List<BudgetPercentages> breakdown = new ArrayList<BudgetPercentages>();
		for (Category category : categories) {
			result = getTotalByCategory(category.getCat(), month, year);
			total += result;
			breakdown.add(new BudgetPercentages(category.getCat(), result, 0));
		}
		for (BudgetPercentages bp : breakdown) {
			bp.setPercent((bp.getTotal() / total) * 100);
		}
		
		return breakdown;
	}
	
	public double totalWithdrawals(int month, int year) {
		return withdrawalDao.totalWithdrawals(month, year);
	}

}
