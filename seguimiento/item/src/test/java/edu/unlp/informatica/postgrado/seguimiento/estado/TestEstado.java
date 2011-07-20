package edu.unlp.informatica.postgrado.seguimiento.estado;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;

public class TestEstado {

	@Test
	public void test() {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
		EstadoService myService = (EstadoService) ctx.getBean("estadoService",EstadoService.class);

		Estado i = new Estado();
		i.setName("abierto");
		myService.save(i);
		i = new Estado();
		i.setName("cerrado");
		myService.save(i);
		
		Assert.assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
	}
}
