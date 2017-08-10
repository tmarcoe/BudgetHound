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
import com.interfaces.ICategories;

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
	public List<Categories> retrieveByParent(int household_id, String parent) {
		Session session = session();
		String hql = "FROM Categories WHERE household_id = :household_id AND parent = :parent";
		List<Categories> catList = session.createQuery(hql).setInteger("household_id", household_id).setString("parent", parent).list();
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
		String hql = "DELETE FROM Categories WHERE id = :id";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("id", categories.getId()).executeUpdate();
		tx.commit();
		session.disconnect();
	}

	public boolean hasSubCateories(int household_id, String category) {
		Session session = session();
		String hql = "SELECT COUNT(*) FROM Categories WHERE household_id = :household_id AND parent = :category";
		long count = (long) session.createQuery(hql).setInteger("household_id", household_id).setString("category", category).uniqueResult();
		
		return (count > 0);
	}

	public void deleteByhouseholdId(int household_id) {
		Session session = session();
		String hql = "DELETE FROM Categories WHERE household_id = :household_id";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("household_id", household_id).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
	public void zeroCategories(int household_id) {
		Session session = session();
		String hql = "UPDATE Categories SET amount = 0 WHERE household_id = :household_id";
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("household_id", household_id).executeUpdate();
		tx.commit();
		session.disconnect();
	}
}
