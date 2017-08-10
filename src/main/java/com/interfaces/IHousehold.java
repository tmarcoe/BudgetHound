package com.interfaces;

import com.entity.Household;

public interface IHousehold {
	public void create(Household household);
	public Household retrieve(int entry_id);
	public void update(Household household);
	public void delete(Household household);
}
