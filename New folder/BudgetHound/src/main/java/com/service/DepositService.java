package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.dao.DepositDao;
import com.entity.Deposit;

@Service
public class DepositService implements IDepositService {
	
	@Autowired
	DepositDao depositDao;

	@Override
	public void create(Deposit deposit) {
		depositDao.create(deposit);
	}

	@Override
	public void update(Deposit deposit) {
		depositDao.update(deposit);
	}

	@Override
	public Deposit retrieve(int entryId) {
		return depositDao.retrieve(entryId);
	}

	@Override
	public PagedListHolder<Deposit> retrieveList() {
		return new PagedListHolder<Deposit>(depositDao.retrieveList());
	}
	
	public List<Deposit> retrieveRawList() {
		return depositDao.retrieveList();
	}

	@Override
	public void delete(Deposit deposit) {
		depositDao.delete(deposit);
	}

}
