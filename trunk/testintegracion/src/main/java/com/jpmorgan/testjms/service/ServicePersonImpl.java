package com.jpmorgan.testjms.service;

import com.jpmorgan.testjms.dao.PersonDAO;
import com.jpmorgan.testjms.model.Person;

public class ServicePersonImpl implements ServicePerson {

	PersonDAO  personDAO;
	
	
	public void save(Person p) {
		
		
		//Transaction tx = personDAO.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
		//personDAO.getHibernateTemplate().setFlushMode(HibernateAccessor.FLUSH_EAGER);
		personDAO.save(p);
		//personDAO.getHibernateTemplate().flush();
		//tx.commit();
//		Person p2 = new Person();
//		p2.setName("pepe");
//		personDAO.save(p);
//		throw new  RuntimeException();
	}
	/**
	 * @return the personDAO
	 */
	public PersonDAO getPersonDao() {
		return personDAO;
	}
	/**
	 * @param personDAO the personDAO to set
	 */
	public void setPersonDao(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}
	public void update(Person p) {

		personDAO.update(p);
		
	}
}
