package com.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	@SuppressWarnings("unchecked")
	public List<Register> retieveList(int household_id, Date start, Date end, String parent) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (start == null) {
			start = sdf.parse("1970-01-01");
		}
		
		if (end == null) {
			end = sdf.parse("2099-12-31");
		}
		
		Session session = session();
		String hql = "FROM Register r, Categories c WHERE r.trans_date BETWEEN :start AND :end AND  c.category = r.category AND c.parent = :parent";
		
		List<Register> rList = session.createQuery(hql).setDate("start", start).setDate("end", end).setString("parent", parent).list();
		
		return rList;
	}

	@Override
	public void update(Register register) {
		Session session =  session();
		Transaction tx = session.beginTransaction();
		session.update(register);
		tx.commit();
		session.disconnect();
	}
	@SuppressWarnings("unchecked")
	public void totalTransactions(int household_id, String parent) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		String hqlList = "FROM Register r, Categories c WHERE r.household_id = :household_id AND c.category = r.category AND c.parent = :parent";
		String hqlUpd = "UPDATE Register SET running_balance = ((:total + deposit) - withdrawal) WHERE entry_id = :entry_id";
		String getTotal = "SELECT running_balance FROM Register WHERE entry_id = :entry_id";
		List<Register> regList = session.createQuery(hqlList).setInteger("household_id", household_id).setString("parent", parent).list();
		double total = 0;
		for(Register reg: regList) {
			session.createQuery(hqlUpd).setDouble("total", total).setInteger("entry_id", reg.getEntry_id()).executeUpdate();
			total = (double) session.createQuery(getTotal).setInteger("entry_id", reg.getEntry_id()).uniqueResult();
		}
		tx.commit();
		session.disconnect();
	}
	
	public double getEndingBalance(int household_id, String parent) {
		Session session = session();
		String hql = "SELECT (SUM(deposit) - SUM(withdrawal)) FROM Register r, Categories c WHERE r.household_id = :household_id AND c.category = r.category AND c.parent = :parent ";
		
		double ending_balance = (double) session.createQuery(hql).setInteger("household_id", household_id).setString("parent", parent).uniqueResult();
		session.disconnect();
		
		return ending_balance;
	}
	public double getBalanceByCategory(int household_id, String category) {
		Session session = session();
		String hql = "SELECT (SUM(deposit) - SUM(withdrawal)) FROM Register WHERE household_id = :household_id AND category = :category OR deposit > 0";
		
		double ending_balance = (double) session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).uniqueResult();
		session.disconnect();
		
		return ending_balance;		
	}
	
	public double getExpenseByCategory(int household_id, String category) {
		Session session = session();
		String hql = "SELECT SUM(withdrawal) FROM Register WHERE household_id = :household_id AND category = :category";
		
		double ending_balance = (double) session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).uniqueResult();

		session.disconnect();
		
		return ending_balance;		
	}
	
	@Override
	public void delete(Register register) {
		Session session =  session();
		String hql = "DELETE FROM Register WHERE entry_id = :entry_id";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("entry_id", register.getEntry_id()).executeUpdate();
		tx.commit();
		session.disconnect();
	}

}
