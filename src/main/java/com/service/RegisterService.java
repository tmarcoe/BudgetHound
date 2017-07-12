package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RegisterDao;
import com.entity.Register;

import interfaces.IRegister;

@Service
public class RegisterService implements IRegister {

	@Autowired
	private RegisterDao registerDao;
	
	@Override
	public void create(Register register) {
		registerDao.create(register);
	}

	@Override
	public Register retrieve(int entry_id) {
		return registerDao.retrieve(entry_id);
	}

	@Override
	public void update(Register register) {
		registerDao.update(register);
	}

	@Override
	public void delete(Register register) {
		registerDao.delete(register);
	}

}
