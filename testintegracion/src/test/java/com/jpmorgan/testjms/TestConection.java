package com.jpmorgan.testjms;

import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;
import org.springframework.test.annotation.Rollback;

import com.jpmorgan.testjms.dao.Person2DAO;
import com.jpmorgan.testjms.model.Person2;

public class TestConection extends AbstractAnnotationAwareTransactionalTests {

	Person2DAO person2Dao;

	protected String[] getConfigLocations() {
		return new String[] { "classpath:testjms_spring2.xml" };
	}

	@Rollback(false)
	public void testSavePerson() {

		Person2 p = new Person2();
		p.setName("yo");
		person2Dao.save(p);
		//personDao.getHibernateTemplate().getSessionFactory().getCurrentSession().getTransaction().commit();
		
	}

	/**
	 * @return the personDao
	 */
	public Person2DAO getPerson2Dao() {
		return person2Dao;
	}

	/**
	 * @param personDao
	 *            the personDao to set
	 */
	public void setPerson2Dao(Person2DAO person2Dao) {
		this.person2Dao = person2Dao;
	}

}