package edu.unlp.informatica.postgrado.seguimiento.item.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class AbstractRepository<E, K> {
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	private final Class<E> clazz  ;

	
	public AbstractRepository(Class<E> clazz) {
		this.clazz = clazz;
	}
	
	public E save(E entity) {
		
		hibernateTemplate.save(entity);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> find() {
		 
		return hibernateTemplate.loadAll(clazz);
	}
	
	public E getById(Long id) {
		return hibernateTemplate.load(clazz, id);
	}

}
