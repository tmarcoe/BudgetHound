package com.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Register;

import interfaces.IRegister;

@Transactional
@Repository
public class RegisterDao implements IRegister {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(Register register) {
		Session session =  session();
		Transaction tx = session.beginTransaction();
		session.save(register);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Register retrieve(int entry_id) {
		Session session =  session();
		Register register = (Register) session.createCriteria(Register.class).add(Restrictions.idEq(entry_id)).uniqueResult();
		session.disconnect();
		
		return register;
	}

	@Override
	public void update(Register register) {
		Session session =  session();
		Transaction tx = session.beginTransaction();
		session.update(register);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Register register) {
		Session session =  session();
		Transaction tx = session.beginTransaction();
		session.delete(register);
		tx.commit();
		session.disconnect();
	}

}
