package edu.unlp.informatica.postgrado.seguimiento.item;

import static junit.framework.Assert.fail;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PrioridadService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ProyectoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

public class TestItem {

	@Test
	public void test() {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
		ctx.scan("edu.unlp.informatica.postgrado.seguimiento.item");
		PersonaService myService = (PersonaService) ctx.getBean("personaService",PersonaService.class);
		ItemService iService = (ItemService) ctx.getBean("itemService",ItemService.class);
		ProyectoService proService = (ProyectoService) ctx.getBean("proyectoService",ProyectoService.class);
		TipoItemService tiService = (TipoItemService) ctx.getBean("tipoItemService",TipoItemService.class);
		EstadoService eService = (EstadoService) ctx.getBean("estadoService",EstadoService.class);
		ConfiguracionItemService ciService = (ConfiguracionItemService) ctx.getBean("configuracionItemService",ConfiguracionItemService.class);
		PrioridadService prService = (PrioridadService) ctx.getBean("prioridadService",PrioridadService.class);
		
		Persona i = new Persona();
		i.setNombre("Jefi");
		try {
			myService.save(i);
			
			Prioridad pr = new Prioridad();
			pr.setNombre("Altisi3");
			prService.save(pr);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliació3");
			tiService.save(ti);
			
			Estado e = new Estado();
			e.setNombre("sss4");
			eService.save(e);
			
			ConfiguracionItem ci = new ConfiguracionItem();
			ci.setEstado(e);
			ci.setTipoItem(ti);
			ci.getProximosEstados().add(e);
			ciService.save(ci);
			
			Proyecto p = new Proyecto();
			p.setLider(i);
			p.setNombre("ppp2");
			p.getTipoItems().put(ti, ci);
			proService.save(p);
			
			
			Item i1 = new Item();
			i1.setTitulo("lu3333");
			i1.setProyecto(p);
			i1.setPrioridad(pr);
			i1.setDescripcion("ss");
			i1.setResponsable(i);
			i1.setTipoItem(ti);
			i1.setEstado(e);
			iService.save(i1);
			
			
			//assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
}
