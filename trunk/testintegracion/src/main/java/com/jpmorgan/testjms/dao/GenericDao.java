package com.jpmorgan.testjms.dao;

import java.io.Serializable;

import org.springframework.orm.hibernate3.HibernateTemplate;

public interface GenericDao <T, PK extends Serializable> {

	public HibernateTemplate getHibernateTemplate();
	
    /** Persist the newInstance object into database */
    PK create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    T load(PK id);

    /** Save changes made to a persistent object.  */
    void save(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);
    
    void update(T persistentObject);
}