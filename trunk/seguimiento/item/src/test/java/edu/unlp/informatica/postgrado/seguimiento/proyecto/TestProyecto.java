package edu.unlp.informatica.postgrado.seguimiento.proyecto;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.ServiceException;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.security.Rol;
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
import static edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado.*;

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
	
	@Autowired
	HibernateTemplate hibernateTemplate;
		
	@Test
	@Rollback
	public void test() {
		
		try {
			Persona i = new Persona();
			i.setUserName("test");
			i.setHabilitado(true);
			i.setPassword("sss");
			i.setNombre("Jefe");
			i.getRoles().add(Rol.LIDER_DE_PROYECTO);
			myService.save(i);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliació2");
			tiService.save(ti);
			
			Estado e = new Estado();
			e.setNombre("Inicial test");
			e.setTipoEstado(INICIAL);
			e = eService.save(e);
			
			Estado e2 = new Estado();
			e2.setNombre("Finalizado test");
			e2.setTipoEstado(FINAL);
			e2 = eService.save(e2);
			
			
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
			i.setUserName("test");
			i.setHabilitado(true);
			i.getRoles().add(Rol.LIDER_DE_PROYECTO);
			i.setPassword("sss");
			i.setNombre("Jefe test");

			myService.save(i);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliación test");
			ti = tiService.save(ti);
			
			TipoItem ti2 = new TipoItem();
			ti2.setNombre("Mejora");
			ti2 = tiService.save(ti2);
						
			Estado e = new Estado();
			e.setNombre("Inicial test");
			e.setTipoEstado(INICIAL);
			e = eService.save(e);
			
			Estado e2 = new Estado();
			e2.setNombre("Finalizado test");
			e2.setTipoEstado(FINAL);
			e2 = eService.save(e2);
			
			
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
			
			List<TipoItem> list = newVersion.getTipoItemList();
			list.add(ti2);
			newVersion.setTipoItemList(list);
					
			proService.update(newVersion);
			p = proService.getById(p.getId());
			
			assertTrue("Los tipo items del proyecto deberian ser 2.", p.getTipoItems().keySet().size() == 2);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}
	
	@Test	
	@Rollback
	public void deleteTipoItemAndConfigItem() {		
		try {
			Persona i = new Persona();
			i.setUserName("test");
			i.setHabilitado(true);
			i.setPassword("sss");
			i.getRoles().add(Rol.LIDER_DE_PROYECTO);
			i.setNombre("Jefe test");

			myService.save(i);
						
			TipoItem ti = new TipoItem();
			ti.setNombre("Ampliación test");
			ti = tiService.save(ti);
			
			TipoItem ti2 = new TipoItem();
			ti2.setNombre("Mejora");
			ti2 = tiService.save(ti2);
			
			TipoItem ti3 = new TipoItem();
			ti3.setNombre("bug");
			ti3 = tiService.save(ti3);
						
			Estado e = new Estado();
			e.setNombre("Inicial test");
			e.setTipoEstado(INICIAL);
			e = eService.save(e);
			
			Estado e2 = new Estado();
			e2.setNombre("Finalizado test");
			e2.setTipoEstado(FINAL);
			e2 = eService.save(e2);
			
			ConfiguracionItem ci = new ConfiguracionItem();
			ConfiguracionEstado confEstado = new ConfiguracionEstado();
			confEstado.setConfiguracionItem(ci);
			confEstado.setEstado(e);
			confEstado.getProximosEstados().add(e2);
			ci.getProximosEstados().put(e, confEstado);
			
			ConfiguracionItem ci1 = new ConfiguracionItem();
			ConfiguracionEstado confEstado1 = new ConfiguracionEstado();
			confEstado1.setConfiguracionItem(ci1);
			confEstado1.setEstado(e);
			confEstado1.getProximosEstados().add(e2);
			ci1.getProximosEstados().put(e, confEstado1);
			
			Proyecto p = new Proyecto();
			p.setLider(i);
			p.setNombre("Proyecto test");
			p.getTipoItems().put(ti, ci);
			p.getTipoItems().put(ti3, ci1);
			ci.setProyecto(p);
			ci.setTipoItem(ti);
			ci1.setProyecto(p);
			ci1.setTipoItem(ti3);
			Proyecto p1 = proService.save(p);
			assertTrue("Debería haberse generado 2 config del item", ciService.find().size() == 2);

			hibernateTemplate.evict(p);
			p1.getTipoItems().remove(ti);
			proService.update(p1);
			
			assertTrue("Debería haberse borrado 1 config del item", ciService.find().size() == 1);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}
//	
//	@Test	
//	@Rollback
//	public void testito() {
//		
//		try {
//			Proyecto newVersion = proService.getById(4L);
//			newVersion.toString();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
