package com.dao;

import java.util.List;

import com.entity.Deposit;


public interface IDepositDao {
	public void create(Deposit deposit);
	public void update(Deposit deposit);
	public Deposit retrieve(int entryId);
	public List<Deposit> retrieveList();
	public void delete(Deposit deposit);
}
