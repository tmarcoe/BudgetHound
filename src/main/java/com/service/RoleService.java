package com.service;

import java.util.List;

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
	
	public Role retrieve(String name) {
		return roleDao.retrieve(name);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public void delete(Role role) {
		roleDao.delete(role);
	}

	public List<Role> retrieveList() {
		
		return roleDao.retrieveList();
	}

}
