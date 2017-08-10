package com.interfaces;

import com.entity.Register;

public interface IRegister {
	public void create(Register register);
	public Register retrieve(int entry_id);
	public void update(Register register);
	public void delete(Register register);
}
