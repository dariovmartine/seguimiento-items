package com.jpmorgan.testjms.dao;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class GenericDaoHibernateImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericDao<T, PK> {
	private Class<T> type;

	public GenericDaoHibernateImpl(Class<T> type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public PK create(T o) {
		return (PK) getSession().save(o);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		return (T) getSession().get(type, id);
	}

	public void save(T o) {
		getSession().save(o);
	}

	public void delete(T o) {
		getSession().delete(o);
	}

	public void update(T o) {
		getSession().update(o);

	}
}