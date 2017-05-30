package com.service;

import org.springframework.beans.support.PagedListHolder;

import com.entity.Deposit;

public interface IDepositService {

	public void create(Deposit deposit);
	public void update(Deposit deposit);
	public Deposit retrieve(int entryId);
	public PagedListHolder<Deposit> retrieveList();
	public void delete(Deposit deposit);
}
