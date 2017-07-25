package com.dao;

import java.util.List;

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

	public Household retrieve(String username) {
		Session session = session();
		Household household = (Household) session.createCriteria(Household.class).add(Restrictions.eq("username", username)).uniqueResult();
		session.disconnect();
		
		return household;
	}
	
	@SuppressWarnings("unchecked")
	public List<Household> retrieveList() {
		Session session = session();
		
		List<Household> houseList = session.createCriteria(Household.class).list();
		session.disconnect();
		
		return houseList;
	}
	
	public boolean exists(String username) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM Household WHERE username = :username";
		
		long count = (long) session.createQuery(hql).setString("username", username).uniqueResult();
		
		return (count > 0);
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
	
	public void delete(int household_id) {
		Session session = session();
		String hql = "DELETE FROM Household where household_id = :household_id";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("household_id", household_id).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	public void merge(Household household) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(household);
		tx.commit();
		session.disconnect();
	}

}
