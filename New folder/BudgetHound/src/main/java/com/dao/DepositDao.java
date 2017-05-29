package com.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Deposit;

@Transactional
@Repository
public class DepositDao implements IDepositDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	private Session session() {
		SessionFactory hibernateFactory;
		Session session = null;
		if((hibernateFactory = (SessionFactory) entityManagerFactory.unwrap(SessionFactory.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		if (hibernateFactory.isClosed() ) {
			session = hibernateFactory.openSession();
		}else{
			session = hibernateFactory.getCurrentSession();
		}
		return session;
	}
	
	@Override
	public void create(Deposit deposit) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(deposit);
		tx.commit();
	}

	@Override
	public void update(Deposit deposit) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(deposit);
		tx.commit();
		
	}

	@Override
	public Deposit retrieve(int entryId) {
		return (Deposit) session().createCriteria(Deposit.class).add(Restrictions.idEq(entryId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Deposit> retrieveList() {
		return session().createCriteria(Deposit.class).list();
	}

	@Override
	public void delete(Deposit deposit) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(deposit);
		tx.commit();
	}

}
