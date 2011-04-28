package edu.unlp.informatica.postgrado;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.item.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;

public class TestConfiguration {

	public void test() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		  ItemService myService = (ItemService) ctx.getBean(ItemService.class.getName());
		  myService.findItems();
	}
}
