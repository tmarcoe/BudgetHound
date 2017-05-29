package com.service;

import org.springframework.beans.support.PagedListHolder;

import com.entity.Withdrawal;

public interface IWithdrawalService {

	public void create(Withdrawal withdrawal);
	public void update(Withdrawal withdrawal);
	public Withdrawal retrieve(int entryId);
	public PagedListHolder<Withdrawal> retrieveList();
	public void delete(Withdrawal withdrawal);
}
