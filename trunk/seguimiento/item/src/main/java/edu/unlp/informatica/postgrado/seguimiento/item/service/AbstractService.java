package edu.unlp.informatica.postgrado.seguimiento.item.service;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.AbstractLazyInitializer;
import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sun.reflect.ReflectionFactory;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Numerable;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.AbstractRepository;

public abstract class AbstractService<E extends Numerable, R extends AbstractRepository<E, ? extends Serializable>> {

			
	public abstract R getRepository();
	
			
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public E save(E entity) throws ServiceException {
		
		try {
			
			E newEntity = (E) getRepository().getClazz().newInstance();
			getRepository().save(entity);
			copyFields(entity, newEntity);
			return newEntity;
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public E update(E entity) throws ServiceException {
		
		try {
			E original = (E) getRepository().getById(entity.getId());
			
			updateProperties(original, entity);
			
			getRepository().update(original);
			
			copyFields(original, entity);
			return entity;
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
			List<E> res = new ArrayList<E>();
			copyFields(getRepository().find(), res);
			return res;
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
			E res = getRepository().getClazz().newInstance();
			copyFields(entity, res);
			return res;
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
	}
		
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,rollbackFor=ServiceException.class)
	public void updateProperties(Object original, Object nueva) throws BeansException {
			
		PropertyDescriptor[] originalPds = BeanUtils.getPropertyDescriptors(original.getClass());
		
		for (PropertyDescriptor originalPd : originalPds) {
			
			if (originalPd.getWriteMethod() != null) {
				
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(nueva.getClass(), originalPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					
					try {
						Method readMethod = sourcePd.getReadMethod();
						Method writeMethod = originalPd.getWriteMethod();
						
						if (! checkAnotations(nueva, sourcePd.getName())) {
							continue;
						}						
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object newValue = readMethod.invoke(nueva);
						readMethod = originalPd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object oldValue = readMethod.invoke(original);

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
							
							String name = writeMethod.getName().substring(3, writeMethod.getName().length()).toLowerCase();
							if (name.equals("tipoitems")) {
								name = "tipoItems";
							}
							
							try {
								Field tooF = original.getClass().getDeclaredField(name);
								
	        					if (! tooF.isAccessible()) {
	  	  	        				tooF.setAccessible(true);
	  	  	        			}
	  	        				
	        					tooF.set(original, newValue);
	  	        			} catch (Exception e) {
	  	        				
	  	        				try {
	  	        					
	  	        					Field fz = original.getClass().getDeclaredField("handler");
	  	        					if (! fz.isAccessible()) {
	  	        						fz.setAccessible(true);
	  	        					}
	  	        					AbstractLazyInitializer a = (AbstractLazyInitializer) fz.get(original);
	  	        					
	  	        					Numerable numerable = (Numerable) a.getImplementation();
	  	        					Field tooF =  numerable.getClass().getDeclaredField(name);
	  	        				
	  	        					if (! tooF.isAccessible()) {
	  	        						tooF.setAccessible(true);
	  	        					}
	  	        				
	  	        					tooF.set(numerable, newValue);
	  	        				} catch (Exception e2) {
	  	        					
	  	        					if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
	  	        						writeMethod.setAccessible(true);
	  	        					}
	  	        					writeMethod.invoke(original, newValue);
	  	        				}
	  	        			}
							
							
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

	public void copyFields(List<E> from, List<E> too) {
		
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		
		for (E e : from) {
			
			try {
				E e2;
				e2 = getRepository().getClazz().newInstance();
				copyFields(e, e2, hashMap);
				too.add(e2);
			} catch (Exception e1) {
						
			}			
		}
  		hashMap.clear();
  	}
	
	public void copyFields(Map<E,E> from, Map<E,E> too) {
		
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		
		for (E e : from.keySet()) {
			
			try {
				E e2;
				e2 = getRepository().getClazz().newInstance();
				copyFields(e, e2, hashMap);
				E v2;
				v2 = getRepository().getClazz().newInstance();
				copyFields(from.get(e), v2, hashMap);
				too.put(e2,v2);
			} catch (Exception e1) {
						
			}			
		}
  		hashMap.clear();
  	}

	
	public void copyFields(E from, E too) {
		
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
  		copyFields(from, too, hashMap);
  		hashMap.clear();
  	}
  	
  	private <T extends Object, Y extends Object> void copyFields(T from, Y too, Map<Object,Object> converted) {

  	    Class<? extends Object> fromClass = from.getClass();
  	    Field[] fromFields = fromClass.getDeclaredFields();

  	    Class<? extends Object> tooClass = too.getClass();
  	    Field[] tooFields = tooClass.getDeclaredFields();

  	    converted.put(from, too);
  	    if (fromFields != null && tooFields != null) {
  	        for (Field tooF : tooFields) {
  	            
  	        	boolean ok = false;
  	        	boolean mustMap = true;
  	        	for (Annotation a : tooF.getAnnotations()) {
  	  			
  	        		if (a.annotationType().getName().indexOf("javax.persistence") >= 0 
  	        				&& a.annotationType().getName().indexOf("javax.persistence.Transient") < 0) {
  	        			ok = true;
  	        		}
  	        	}
  	        	for (Annotation a : tooF.getAnnotations()) {
  	    	  				
  	        		if (a instanceof MappingOptions) {
  	        			
  	        			MappingOptions ma = (MappingOptions) a;
  	  				
  	        			for (Class clazz : ma.exclude()) {
  	  				
  	        				if (clazz == this.getClass()) {
  	        					mustMap = false;
  	        				}
  	        			}
  	        		}	
  	        	}	
  	        	if (ok) {
  	  		
  	        		try {
        				Object value = null;
        				try {
        					if (! tooF.isAccessible()) {
  	  	        				tooF.setAccessible(true);
  	  	        			}
  	        				Field fromF = fromClass.getDeclaredField(tooF.getName());
  	        				value = fromF.get(from);
  	        			} catch (Exception e) {
  	        				
  	        			}
  	        			if (value == null) {
  	        				try {
  	        					Method method = fromClass.getDeclaredMethod("get" + StringUtils.capitalize(tooF.getName()));
  	        					value = method.invoke(from);
  	        				} catch (Exception e) { 
  	                		
  	        				}
  	        			}	
  	        			if (value == null) {
  	        				
  	        				try {
  	        					Method method = fromClass.getDeclaredMethod("is" + StringUtils.capitalize(tooF.getName()));
  	        					value = method.invoke(from);
  	        				} catch (Exception e) { 
  	                		
  	        				}
  	        			}	
  	                	        				
  	        			if (value != null) {
  	        			  	                	        					
  	        				if (value.getClass().getPackage().getName().indexOf("java.lang") < 0) {
  	        					
  	        					if (converted.get(value) == null) {
  	                				
  	        						Object value2 = null;
  	                				
  	                				if (value instanceof List) {
  	                					value2 = new ArrayList();
  	                					if (mustMap) {
 	                						for (Object o : ((List)value)) {
  	                						
                								Object o2 = getInstance(o, converted);
  	                						
  	                        					((List)value2).add(o2);
                							} 
                						}
  	                					converted.put(value, value2);
                						tooF.set(too, value2);
                					} else if (value instanceof Map) {
                						value2 = new HashMap();
                						if (mustMap) {
                							for (Object o : ((Map)value).keySet()) {
  	                							Object o2 = getInstance(o, converted);
  	                							Object v2 = getInstance(((Map)value).get(o), converted);
  	                							((HashMap)value2).put(o2, v2);
  	                						}
  	                					}
                						converted.put(value, value2);
  	                					tooF.set(too, value2);
  	                				} else {
  	                					if (mustMap) {
  	                						value2 = getInstance(value, converted);
  	                						tooF.set(too, value2);
  	                					}
  	                				}  	                		
  	                					
 	        					} else {
  	        						if (mustMap) {
  	        							tooF.set(too, converted.get(value));
  	        						}
  	                			}
  	        				} else {
  	        					if (mustMap) {
  	        						tooF.set(too, value);
  	        					}
  	                		}
  	        			}
  	        		
  	        		} catch (Exception e) {
  	                	e.printStackTrace();
  	            	} 
  	        	}
  	        }
  	    }
  	}
  	
  	private Object getInstance(Object o, Map<Object,Object> converted) 
  		throws SecurityException, NoSuchMethodException, IllegalArgumentException, 
  			InstantiationException, IllegalAccessException, InvocationTargetException {
  		
  		Object o2 = null;

  		if (converted.get(o) != null) {
  			
  			o2 = converted.get(o);
  		} else	if (o instanceof Enum) {
  			
			o2 = ((Enum)o).valueOf(((Enum) o).getDeclaringClass(), ((Enum) o).name());
  		} else	if (Arrays.asList(HibernateProxyHelper.getClassWithoutInitializingProxy(o).getInterfaces()).contains(Numerable.class)) {
  			
  			o2 = o.getClass().newInstance();
  			copyFields(o,o2,converted);
  		} else {
			
			ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
			Constructor objDef = Object.class.getDeclaredConstructor();
			Constructor intConstr = rf.newConstructorForSerialization(o.getClass(), objDef);
			o2 = o.getClass().cast(intConstr.newInstance());
			copyFields(o,o2,converted);
		}
  		return o2;
  	}
}
