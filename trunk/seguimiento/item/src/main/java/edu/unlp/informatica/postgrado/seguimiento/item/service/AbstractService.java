package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.mapper.DefaultDozerBeanMapper;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Numerable;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.AbstractRepository;

public abstract class AbstractService<E extends Numerable, R extends AbstractRepository<E, ? extends Serializable>> {

	public abstract DefaultDozerBeanMapper<E,? extends AbstractService<E, R>> getMapper(); 
		
	public abstract R getRepository();
	
	@PostConstruct
	public void postConstruct() {
		
		getMapper().setS(this);
	}
			
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public E save(E entity) throws ServiceException {
		
		try {
			
			E newEntity = (E) getRepository().save(entity);
			return (E) getMapper().map(newEntity, entity.getClass());	
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public E update(E entity) throws ServiceException {
		
		try {
			E original = (E) getRepository().getById(entity.getId());
			updateProperties(original, entity);		
			E updateEntity = (E) getRepository().update(original);
			return (E) getMapper().map(updateEntity, entity.getClass());
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
			
			return getMapper().map(getRepository().find(), ArrayList.class);
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
			return (E) getMapper().map(entity, entity.getClass());
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}
		
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public void updateProperties(Object source, Object target) throws BeansException {
			
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(source.getClass());
		
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(target.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						Method writeMethod = targetPd.getWriteMethod();
						if (! checkAnotations(target, sourcePd.getName())) {
							continue;
						}
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object newValue = readMethod.invoke(target);
						readMethod = targetPd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object oldValue = readMethod.invoke(source);

						// Solo seteo valores distintos
						// Pero que haces cuando queres poner un null? Hay que usar NullObject?
						if ((newValue == null && oldValue == null) || (newValue != null && ! newValue.equals(oldValue))) { 
							
							// Es un map
							if (newValue instanceof Map) {
								Map newMap = (Map) newValue;
								Map oldMap = (Map) oldValue;
								Object arr[] =  newMap.keySet().toArray();
								for (Object key : arr) {
									if (oldMap.containsKey(key)) {
										updateProperties(oldMap.get(key), newMap.get(key));
									} else {
										oldMap.put(key, newMap.get(key));
									}
								}
								arr =  oldMap.keySet().toArray();
								for (Object key : arr) {
									if (! newMap.containsKey(key)) {
										oldMap.remove(key);
									}
								}
								newValue = oldValue;
							}
							
							// Es un list
							if (newValue instanceof List) {
								List newList = (List) newValue;
								List oldList = (List) oldValue;
								
								Object arr[] =  newList.toArray();
								for (Object value : arr) {
									if (oldList.contains(value)) {
										updateProperties(value, newList.get(newList.indexOf(value)));
									} else {
										oldList.add(value);
									}
								}
								arr =  oldList.toArray();
								for (Object value : arr) {
									if (! newList.contains(value)) {
										oldList.remove(value);
									}
								}
								newValue = oldValue;
							}
							
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(source, newValue);
						}
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	
	private boolean checkAnotations(Object obj, String fieldName) {
		
		for (Field field: obj.getClass().getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				for (Annotation a : field.getAnnotations()) {
					if (a.annotationType().getName().indexOf("javax.persistence.") >= 0)
						return true;
				}		
			}
		}
		
		  
		return false;
	}
}
