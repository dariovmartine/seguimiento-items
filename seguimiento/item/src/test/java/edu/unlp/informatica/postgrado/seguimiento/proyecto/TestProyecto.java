package edu.unlp.informatica.postgrado.seguimiento.proyecto;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
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
		ConfiguracionItemService ciService = (ConfiguracionItemService) ctx.getBean("configuracionItemService",ConfiguracionItemService.class);
		
		Persona i = new Persona();
		i.setNombre("Jefe");
		try {
			myService.save(i);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliaci�2");
			tiService.save(ti);
			
			Estado e = new Estado();
			e.setNombre("Inicial");
			eService.save(e);
			
			Estado e2 = new Estado();
			e2.setNombre("Finalizado");
			eService.save(e2);
			
			ConfiguracionItem ci = new ConfiguracionItem();
			ConfiguracionEstado confEstado = new ConfiguracionEstado();
			confEstado.setEstado(e);
			confEstado.setConfiguracionItem(ci);
			confEstado.getProximosEstados().add(e2);
			ci.getProximosEstados().put(e, confEstado);
			//ciService.save(ci);
			
			Proyecto p = new Proyecto();
			p.setLider(i);
			p.setNombre("ppp");
			p.getTipoItems().put(ti, ci);
			ci.setProyecto(p);
			ci.setTipoItem(ti);
			proService.save(p);
			
			assertTrue("Deber�a haberse grabado algo.", myService.find().size() > 0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
	}
}
