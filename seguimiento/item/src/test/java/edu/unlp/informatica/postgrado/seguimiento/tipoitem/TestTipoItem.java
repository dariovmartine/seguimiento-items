package edu.unlp.informatica.postgrado.seguimiento.tipoitem;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

public class TestTipoItem {

	@Test
	public void test() {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
		TipoItemService myService = (TipoItemService) ctx.getBean("tipoItemService",TipoItemService.class);

		TipoItem i = new TipoItem();
		i.setNombre("Reporte de bug");
		try {
			myService.save(i);
			i = new TipoItem();
			i.setNombre("Ampliación");
			myService.save(i);
			
			i = new TipoItem();
			i.setNombre("Mejora");
			myService.save(i);
			
			i = new TipoItem();
			i.setNombre("Nuevo requerimiento");
			myService.save(i);
			
			assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
