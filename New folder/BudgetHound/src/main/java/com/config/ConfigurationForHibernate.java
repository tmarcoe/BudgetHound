package com.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationForHibernate {
	@SuppressWarnings("unused")
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/*
	@Bean
	public SessionFactory getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    SessionFactory session = entityManagerFactory.unwrap(SessionFactory.class);
	    return session;
	}
	*/

}