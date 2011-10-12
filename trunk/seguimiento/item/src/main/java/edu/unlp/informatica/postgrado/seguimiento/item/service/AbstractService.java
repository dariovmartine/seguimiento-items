package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.AbstractRepository;

public abstract class AbstractService<E, R extends AbstractRepository> {

	@Autowired
	private DozerBeanMapper mapper; 
		
	public abstract R getRepository();
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public E save(E entity) throws ServiceException {
		
		try {
			E newEntity = (E) getRepository().save(entity);
			return (E) mapper.map(newEntity, entity.getClass());	
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public E update(E entity) throws ServiceException {
		
		try {
			E updateEntity = (E) getRepository().update(entity);
			return (E) mapper.map(updateEntity, entity.getClass());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<E> find() throws ServiceException {
		
		try {
			return mapper.map(getRepository().find(), ArrayList.class);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<E> find(int first, int count, String sortParam ) throws ServiceException {
	
		try {
			return find();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int getCount() throws ServiceException {
		
		try {
			return getRepository().getCount();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public E getById(Long id) throws ServiceException {
		
		try {
			E entity = (E) getRepository().getById(id);
			return (E) mapper.map(entity, entity.getClass());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
