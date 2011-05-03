package edu.unlp.informatica.postgrado.seguimiento.item;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;

public class TestConfiguration {

	@Test
	public void test() {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
		ItemService myService = (ItemService) ctx.getBean("itemService",ItemService.class);

		Item i = new Item();
		i.setName("dario");
		myService.save(i);
		Assert.assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
	}
}
