package com.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Categories;

import interfaces.ICategories;

@Transactional
@Repository
public class CategoriesDao implements ICategories {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Categories categories) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(categories);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Categories retrieve(int entry_id) {
		Session session = session();
		Categories categories = (Categories) session.createCriteria(Categories.class).add(Restrictions.idEq(entry_id)).uniqueResult();
		session.disconnect();
		return categories;
	}

	@Override
	public void update(Categories categories) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(categories);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Categories categories) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(categories);
		tx.commit();
		session.disconnect();
	}

}
