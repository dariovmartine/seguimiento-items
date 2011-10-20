package edu.unlp.informatica.postgrado.seguimiento.item;

import static junit.framework.Assert.fail;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;

public class TestConfiguration {

	@Test
	public void test() {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
		ItemService myService = (ItemService) ctx.getBean("itemService",ItemService.class);
		EstadoService estadoService = (EstadoService) ctx.getBean("estadoService",EstadoService.class);
		
		Estado s = new Estado();
		s.setNombre("desarrollo");
		
		try {
			estadoService.save(s);
			
			Item i = new Item();
			i.setTitulo("dario3");
						
			//myService.save(i);
			i = new Item();
			i.setTitulo("lu3333");
			
			//myService.save(i);
			
			//assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
}
