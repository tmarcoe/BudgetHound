package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.dao.WithdrawalDao;
import com.entity.Withdrawal;

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

}
