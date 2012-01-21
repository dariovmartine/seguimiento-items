package edu.unlp.informatica.postgrado.seguimiento.proyecto;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.hibernate.validator.constraints.ScriptAssert.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ConfiguracionItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.EstadoRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PersonaRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ProyectoRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.TipoItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ProyectoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={
	AppConfig.class, 
	PersonaService.class,
	ProyectoService.class,
	TipoItemService.class, 
	EstadoService.class, 
	ConfiguracionItemService.class,
	PersonaRepository.class,
	ProyectoRepository.class,
	TipoItemRepository.class, 
	EstadoRepository.class, 
	ConfiguracionItemRepository.class
})
@Transactional
public class TestProyecto {

	@Autowired
	PersonaService myService;
	
	@Autowired
	ProyectoService proService;
	
	@Autowired
	TipoItemService tiService; 
	
	@Autowired
	EstadoService eService; 
	
	@Autowired
	ConfiguracionItemService ciService; 
		
	@Test
	@Rollback
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
			
			assertTrue("Debería haberse grabado un proyecto.", proService.find().size() == 1);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}
	
	@Test	
	@Rollback
	public void updateTipoItem() {
		
		
		try {
			
			
			Persona i = new Persona();
			i.setNombre("Jefe test");

			myService.save(i);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliación test");
			tiService.save(ti);
			
			Estado e = new Estado();
			e.setNombre("Inicial test");
			eService.save(e);
			
			Estado e2 = new Estado();
			e2.setNombre("Finalizado test");
			eService.save(e2);
			
			ConfiguracionItem ci = new ConfiguracionItem();
			ConfiguracionEstado confEstado = new ConfiguracionEstado();
			confEstado.setConfiguracionItem(ci);
			confEstado.getProximosEstados().add(e2);
			ci.getProximosEstados().put(e, confEstado);
			
			Proyecto p = new Proyecto();
			p.setLider(i);
			p.setNombre("Proyecto test");
			p.getTipoItems().put(ti, ci);
			ci.setProyecto(p);
			ci.setTipoItem(ti);
			proService.save(p);
			
			assertTrue("Debería haberse grabado un proyecto", proService.find().size() == 1);

			Proyecto newVersion = proService.getById(p.getId());
			ConfiguracionItem ci2 = new ConfiguracionItem();
			ConfiguracionEstado confEstado2 = new ConfiguracionEstado();
			confEstado2.setConfiguracionItem(ci2);
			confEstado2.getProximosEstados().add(e2);
			ci2.getProximosEstados().put(e, confEstado2);
			ci2.setProyecto(newVersion);
			
			TipoItem ti2 = new TipoItem();
			ti2.setNombre("Mejora");
			tiService.save(ti2);
			ci2.setTipoItem(ti2);
			newVersion.getTipoItems().put(ti2, ci2);
			
			p.copyValues(newVersion);
			
			proService.update(p);
			p = proService.getById(p.getId());
			
			assertTrue("Los tipo items del proyecto deberian ser 2.", p.getTipoItems().keySet().size() == 2);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
		
	}
}
