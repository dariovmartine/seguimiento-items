package edu.unlp.informatica.postgrado.seguimiento.item;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;

@ContextConfiguration(locations={"classpath:item_spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestXMLConfiguration {

	@Autowired
	ItemService  myService;

	@Test
	public void test() {
		
		Item i = new Item();
		i.setName("dario");
		myService.save(i);
		i = new Item();
		i.setName("lu");
		myService.save(i);
		
		Assert.assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
	}
}
