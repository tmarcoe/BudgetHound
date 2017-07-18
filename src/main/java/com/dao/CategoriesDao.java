package com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
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
	
	public Categories retrieve(int household_id, String name) {
		
		Session session = session();
		Criterion res = Restrictions.and(Restrictions.eq("household_id", household_id), Restrictions.eq("name", name));
		Categories categories = (Categories) session.createCriteria(Categories.class).add(res).uniqueResult();
		session.disconnect();
		return categories;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> retrieveSubCategories(int household_id, String category) {
		Session session = session();
		String hql = "SELECT category from Categories WHERE household_id = :household_id AND parent = :category";
		List<String> catList = session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).list();
		session.disconnect();
		
		return catList;
	}
	@SuppressWarnings("unchecked")
	public List<Categories> retrieveList(int household_id, String category) {
		Session session = session();
		String hql = "FROM Categories WHERE household_id = :household_id AND parent = :category";
		List<Categories> catList = session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).list();
		session.disconnect();
		
		return catList;
	}

	public Categories retrieveCategoryByName(int household_id, String category) {
		
		Session session = session();
		String hql = "FROM Categories WHERE household_id = :household_id AND category = :category";
		Categories cat = (Categories) session.createQuery(hql)
											 .setInteger("household_id", household_id)
											 .setString("category", category)
											 .uniqueResult();
		session.disconnect();
		
		return cat;
	}
	
	
	public boolean exists(int household_id, String category) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM Categories WHERE household_id = :household_id AND category = :category";
		long count = (long) session.createQuery(hql)
				 .setInteger("household_id", household_id)
				 .setString("category", category)
				 .uniqueResult();
		session.disconnect();
		
		return (count > 0);
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
