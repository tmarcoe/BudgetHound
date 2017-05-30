package com.dao;

import java.util.List;


import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Category;

@Transactional
@Repository
public class CategoryDao implements ICategoryDao {

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
	public void create(Category category) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(category);
		tx.commit();

	}

	@Override
	public void update(Category category) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(category);
		tx.commit();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> retrieveList() {
		List<Category> catList = (List<Category>) session().createCriteria(Category.class).list();
		session().disconnect();
		
		return catList;
	}

	@Override
	public Category retreive(String cat) {
		Criteria crit = session().createCriteria(Category.class);
		Category category = (Category) crit.add(Restrictions.idEq(cat)).uniqueResult();
		session().disconnect();
		
		return category;
	}

	@Override
	public void delete(Category category) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(category);
		tx.commit();

	}

}
