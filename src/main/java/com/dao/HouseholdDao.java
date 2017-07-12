package com.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Household;

import interfaces.IHousehold;

@Transactional
@Repository
public class HouseholdDao implements IHousehold {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Household household) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(household);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Household retrieve(int entry_id) {
		Session session = session();
		Household household = (Household) session.createCriteria(Household.class).add(Restrictions.idEq(entry_id)).uniqueResult();
		session.disconnect();
		
		return household;
	}

	@Override
	public void update(Household household) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(household);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Household household) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(household);
		tx.commit();
		session.disconnect();
	}

}
