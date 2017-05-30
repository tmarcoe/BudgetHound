package com.dao;

import java.util.List;

import com.entity.Withdrawal;

public interface IWithdrawalDao {
	public void create(Withdrawal withdrawal);
	public void update(Withdrawal withdrawal);
	public Withdrawal retrieve(int entryId);
	public List<Withdrawal> retrieveList();
	public void delete(Withdrawal withdrawal);
}
