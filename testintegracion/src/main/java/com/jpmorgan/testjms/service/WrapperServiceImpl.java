package com.jpmorgan.testjms.service;

import com.jpmorgan.testjms.model.Person;

public class WrapperServiceImpl implements WrapperService {

	ServicePerson servicePerson;
	

		public void savePerson(Person p) {
		servicePerson.save(p);
		Person p2 = new Person();
		p2.setId(p.getId());
		p2.setName("pepe");
		servicePerson.update(p2);
		//throw new RuntimeException();
		p2.toString();
	}

	/**
	 * @return the servicePerson
	 */
	public ServicePerson getServicePerson() {
		return servicePerson;
	}

	/**
	 * @param servicePerson the servicePerson to set
	 */
	public void setServicePerson(ServicePerson servicePerson) {
		this.servicePerson = servicePerson;
	}

}
