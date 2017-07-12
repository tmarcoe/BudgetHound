package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RoleDao;
import com.entity.Role;

import interfaces.IRole;

@Service
public class RoleService implements IRole {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public void create(Role role) {
		roleDao.create(role);
	}

	@Override
	public Role retrieve(int id) {
		return roleDao.retrieve(id);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public void delete(Role role) {
		roleDao.delete(role);
	}

}
