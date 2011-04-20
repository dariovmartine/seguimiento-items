package com.jpmorgan.testjms.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1890734587892345L;

	  
	@Id 
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true)
	@GeneratedValue(generator="EXAMPLE_ID_GEN", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="EXAMPLE_ID_GEN", sequenceName="SEQ_PERSON_ID")
	Long id;
	
	@Basic
    @Column(name = "NAME")
	String name;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
		
		
}
