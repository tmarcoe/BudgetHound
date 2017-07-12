package com.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Role;

import interfaces.IRole;

@Transactional
@Repository
public class RoleDao implements IRole {

	@Autowired
	private SessionFactory sessionFactory;
	
	Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Role role) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(role);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Role retrieve(int id) {
		Session session = session();
		Role role = (Role) session.createCriteria(Role.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		return role;
	}

	@Override
	public void update(Role role) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(role);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Role role) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(role);
		tx.commit();
		session.disconnect();
	}

}