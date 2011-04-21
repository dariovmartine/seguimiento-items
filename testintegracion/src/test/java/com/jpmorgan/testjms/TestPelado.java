package com.jpmorgan.testjms;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jpmorgan.testjms.model.Person;
import com.jpmorgan.testjms.service.ServicePerson;

public class TestPelado extends TestCase {

	public void test() {
		try {
			BeanFactory bf = new ClassPathXmlApplicationContext(
					"classpath:testjms_spring.xml");
			ServicePerson ps = (ServicePerson) bf.getBean("servicePerson");
			// WrapperService ps = (WrapperService)
			// bf.getBean("personServicePelado");
			Person p = new Person();
			p.setName("yo");
			ps.save(p);
			p.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
