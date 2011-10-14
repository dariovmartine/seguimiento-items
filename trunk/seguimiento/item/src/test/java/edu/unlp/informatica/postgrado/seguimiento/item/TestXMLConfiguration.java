package edu.unlp.informatica.postgrado.seguimiento.item;

import static junit.framework.Assert.*;

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
		i.setName("juan");
		i.setState("sdsd");
		try {
			myService.save(i);
			i = new Item();
			i.setName("mariano");
			i.setState("sdsd");
			myService.save(i);
			
			assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
