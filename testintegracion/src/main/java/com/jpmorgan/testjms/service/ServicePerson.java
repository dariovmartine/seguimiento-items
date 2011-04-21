package com.jpmorgan.testjms.service;

import com.jpmorgan.testjms.model.Person;

public interface ServicePerson {

	void save(Person p);

	void update(Person p);
}
