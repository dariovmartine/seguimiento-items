package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.AbstractRepository;

public abstract class AbstractService<E, R extends AbstractRepository> {

	@Autowired
	private DefaultDozerBeanMapper mapper; 
		
	public abstract R getRepository();
			
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public E save(E entity) throws ServiceException {
		
		try {
			
			E newEntity = (E) getRepository().save(entity);
			return (E) mapper.map(newEntity, entity.getClass());	
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public E update(E entity) throws ServiceException {
		
		try {
			
			E updateEntity = (E) getRepository().update(entity);
			return (E) mapper.map(updateEntity, entity.getClass());
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public void delete(E entity) throws ServiceException {
		
		try {

			getRepository().delete(entity);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public List<E> find() throws ServiceException {
		
		try {
			
			return mapper.map(getRepository().find(), ArrayList.class);
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public List<E> find(int first, int count, String sortParam ) throws ServiceException {
	
		try {
			
			return find();
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public long getCount() throws ServiceException {
		
		try {
			
			return getRepository().getCount();
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public E getById(Long id) throws ServiceException {
		
		try {
			
			E entity = (E) getRepository().getById(id);
			return (E) mapper.map(entity, entity.getClass());
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}

	public DefaultDozerBeanMapper getMapper() {
		return mapper;
	}
	
	public void updateProperties(Object source, Object target) throws BeansException {

			
		Class<?> actualEditable = target.getClass();

		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
						try {
							Method readMethod = sourcePd.getReadMethod();
							Method writeMethod = targetPd.getWriteMethod();
							if (writeMethod.isAnnotationPresent(Updateable.class)) {
								continue;
							}
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object newValue = readMethod.invoke(source);

							readMethod = targetPd.getReadMethod();
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object oldValue = readMethod.invoke(source);

							// Solo seteo valores distintos
							if ((newValue == null && oldValue == null) || (newValue != null && ! newValue.equals(oldValue))) { 
								
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, newValue);
							}
						}
						catch (Throwable ex) {
							throw new FatalBeanException("Could not copy properties from source to target", ex);
						}
					}
			}
		}
	}
}
