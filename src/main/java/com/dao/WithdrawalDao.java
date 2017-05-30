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

import com.entity.Withdrawal;

@Transactional
@Repository
public class WithdrawalDao implements IWithdrawalDao {

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
	public void create(Withdrawal withdrawal) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(withdrawal);
		tx.commit();
	}

	@Override
	public void update(Withdrawal withdrawal) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(withdrawal);
		tx.commit();
	}

	@Override
	public Withdrawal retrieve(int entryId) {
		return (Withdrawal) session().createCriteria(Withdrawal.class).add(Restrictions.idEq(entryId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Withdrawal> retrieveList() {
		return session().createCriteria(Withdrawal.class).list();
	}

	@Override
	public void delete(Withdrawal withdrawal) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(withdrawal);
		tx.commit();
	}

	public double getTotalByCategory(String category, int month, int year) {
		String hql = "SELECT SUM(amount) FROM Withdrawal WHERE category = :category AND MONTH(entryDate) = :month AND YEAR(entryDate) = :year";
		Session session = session();
		double retVal = 0.0;
		Object obj;
		obj = session.createQuery(hql)
							   .setString("category", category)
							   .setInteger("month", month)
							   .setInteger("year", year)
							   .uniqueResult();
		 if (obj == null) {
			 retVal = 0.0;
		 }else{
			 retVal = Double.valueOf(String.valueOf((Double)obj));
		 }
		 
		 return retVal;
	}

	public double totalWithdrawals(int month, int year) {
		double retVal;
		String hql = "SELECT SUM(amount) FROM Withdrawal WHERE MONTH(entryDate) = :month AND YEAR(entryDate) = :year";
		Session session = session();
		Object obj = session.createQuery(hql).setInteger("month", month).setInteger("year", year).uniqueResult();
		
		if (obj == null) {
			retVal = 0.0;
		}else{
			retVal = Double.valueOf(String.valueOf((Double)obj));
		}
		return retVal;
	}

}
