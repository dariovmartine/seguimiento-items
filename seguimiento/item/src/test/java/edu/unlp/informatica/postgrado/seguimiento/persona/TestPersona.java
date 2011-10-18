package edu.unlp.informatica.postgrado.seguimiento.persona;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;

public class TestPersona {

	@Test
	public void test() {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
		PersonaService myService = (PersonaService) ctx.getBean("personaService",PersonaService.class);

		Persona i = new Persona();
		i.setNombre("Juan");
		try {
			myService.save(i);
			i = new Persona();
			i.setNombre("Lu");
			myService.save(i);
			
			i = new Persona();
			i.setNombre("Dario");
			myService.save(i);
			
			assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
