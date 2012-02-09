package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class AbstractRepository<E, K extends Serializable> {
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	private final Class<E> clazz  ;

	
	public AbstractRepository(Class<E> clazz) {
		this.clazz = clazz;
	}
	
	public E save(E entity) {
		
		hibernateTemplate.save(entity);
		hibernateTemplate.flush(); // Chequeamos fallas en los constraints
		return entity;
	}
	
	public E update(E entity) {
		
		hibernateTemplate.update(entity);
		hibernateTemplate.flush(); // Chequeamos fallas en los constraints
		return entity;
	}
	
	public E delete(E entity) {
		
		hibernateTemplate.delete(entity);
		hibernateTemplate.flush(); // Chequeamos fallas en los constraints
		return entity;
	}
	
	public List<E> find() {
		 
		return hibernateTemplate.loadAll(clazz);
	}
		
	public E getById(Long id) {
		return hibernateTemplate.load(clazz, id);
	}
	
	public DetachedCriteria getCriteria() {
		return DetachedCriteria.forClass(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findByCriteria(DetachedCriteria c) {
		 
		return hibernateTemplate.findByCriteria(c);
	}
	
	public long getCount() {
		return (Long)hibernateTemplate.findByCriteria(DetachedCriteria.forClass(clazz).setProjection(	Projections.rowCount())).get(0);
	}

}
