package edu.unlp.informatica.postgrado.seguimiento.proyecto;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ProyectoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

public class TestProyecto {

	@Test
	public void test() {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
		PersonaService myService = (PersonaService) ctx.getBean("personaService",PersonaService.class);
		ProyectoService proService = (ProyectoService) ctx.getBean("proyectoService",ProyectoService.class);
		TipoItemService tiService = (TipoItemService) ctx.getBean("tipoItemService",TipoItemService.class);
		EstadoService eService = (EstadoService) ctx.getBean("estadoService",EstadoService.class);
		
		Persona i = new Persona();
		i.setNombre("Jefe");
		try {
			myService.save(i);
			i = new Persona();
			
			TipoItem ti = new TipoItem();
			i.setNombre("Ampliación");
			tiService.save(ti);
			
			Estado e = new Estado();
			e.setNombre("s");
			eService.save(e);
			
			ConfiguracionItem ci = new ConfiguracionItem();
			ci.setTipoItem(ti);
			ci.setEstado(e);
			Proyecto p = new Proyecto();
			p.setLider(i);
			
			p.getTipoItems().put(ti, ci);
			proService.save(p);
			
			assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
