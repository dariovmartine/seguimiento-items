package edu.unlp.informatica.postgrado.seguimiento.proyecto;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;

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


	PersonaService myService;
	ProyectoService proService;
	TipoItemService tiService; 
	EstadoService eService; 
	ConfiguracionItemService ciService; 
	AnnotationConfigApplicationContext ctx;
	
	
	public  TestProyecto() {
		ctx = new AnnotationConfigApplicationContext(
			AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
	}
	
	@Before
	public void initTest() {
		
		myService = (PersonaService) ctx.getBean("personaService",PersonaService.class);
		proService = (ProyectoService) ctx.getBean("proyectoService",ProyectoService.class);
		tiService = (TipoItemService) ctx.getBean("tipoItemService",TipoItemService.class);
		eService = (EstadoService) ctx.getBean("estadoService",EstadoService.class);
		ciService = (ConfiguracionItemService) ctx.getBean("configuracionItemService",ConfiguracionItemService.class);
	}
	
	@Test
	public void test() {
		
		try {
			Persona i = new Persona();
			i.setNombre("Jefe");

			myService.save(i);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliació2");
			tiService.save(ti);
			
			Estado e = new Estado();
			e.setNombre("Inicial");
			eService.save(e);
			
			Estado e2 = new Estado();
			e2.setNombre("Finalizado");
			eService.save(e2);
			
			ConfiguracionItem ci = new ConfiguracionItem();
			ConfiguracionEstado confEstado = new ConfiguracionEstado();
			confEstado.setConfiguracionItem(ci);
			confEstado.getProximosEstados().add(e2);
			ci.getProximosEstados().put(e, confEstado);
			
			Proyecto p = new Proyecto();
			p.setLider(i);
			p.setNombre("ppp");
			p.getTipoItems().put(ti, ci);
			ci.setProyecto(p);
			ci.setTipoItem(ti);
			proService.save(p);
			
			assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}
	
	@Test	
	public void updateTipoItem() {
		
		
		try {
			
			
			Persona i = new Persona();
			i.setNombre("Jefe3");

			myService.save(i);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliació21");
			tiService.save(ti);
			
			Estado e = new Estado();
			e.setNombre("Inicial22");
			eService.save(e);
			
			Estado e2 = new Estado();
			e2.setNombre("Finalizado22");
			eService.save(e2);
			
			ConfiguracionItem ci = new ConfiguracionItem();
			ConfiguracionEstado confEstado = new ConfiguracionEstado();
			confEstado.setConfiguracionItem(ci);
			confEstado.getProximosEstados().add(e2);
			ci.getProximosEstados().put(e, confEstado);
			
			Proyecto p = new Proyecto();
			p.setLider(i);
			p.setNombre("ppp4");
			p.getTipoItems().put(ti, ci);
			ci.setProyecto(p);
			ci.setTipoItem(ti);
			proService.save(p);
			
			assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);

			
			ConfiguracionItem ci2 = new ConfiguracionItem();
			ConfiguracionEstado confEstado2 = new ConfiguracionEstado();
			confEstado2.setConfiguracionItem(ci2);
			confEstado2.getProximosEstados().add(e2);
			ci2.getProximosEstados().put(e, confEstado2);
			ci2.setProyecto(p);
			
			TipoItem ti2 = new TipoItem();
			ti2.setNombre("Ampliació3345");
			tiService.save(ti2);
			ci2.setTipoItem(ti2);
			p.getTipoItems().put(ti2, ci2);
			
			proService.update(p);
			
			assertTrue("Debería haberse grabado algo.", p.getTipoItems().keySet().size() == 2);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
		
	}
}
