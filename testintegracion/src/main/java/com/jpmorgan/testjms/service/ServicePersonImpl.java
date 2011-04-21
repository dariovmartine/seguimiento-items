package com.jpmorgan.testjms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jpmorgan.testjms.dao.PersonDAO;
import com.jpmorgan.testjms.model.Person;

@Component("servicePerson")
public class ServicePersonImpl implements ServicePerson {

	@Autowired
	private PersonDAO personDao;

	@Autowired
	private AnnotationSessionFactoryBean sessionFactory;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(Person p) {

		sessionFactory.toString();
		// Transaction tx =
		// personDAO.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction();
		// personDAO.getHibernateTemplate().setFlushMode(HibernateAccessor.FLUSH_EAGER);
		personDao.save(p);
		// personDAO.getHibernateTemplate().flush();
		// tx.commit();
		// Person p2 = new Person();
		// p2.setName("pepe");
		// personDAO.save(p);
		// throw new RuntimeException();
	}

	public void update(Person p) {
		personDao.update(p);
	}
}
