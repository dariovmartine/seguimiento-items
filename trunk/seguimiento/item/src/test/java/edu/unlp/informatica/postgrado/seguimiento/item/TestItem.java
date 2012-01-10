package edu.unlp.informatica.postgrado.seguimiento.item;

import static junit.framework.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import edu.unlp.informatica.postgrado.seguimiento.item.model.TipoItem;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ConfiguracionItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.EstadoRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PersonaRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.PrioridadRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.ProyectoRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.repository.TipoItemRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ConfiguracionItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PersonaService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PrioridadService;
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
	ItemService.class,
	PrioridadService.class,
	PersonaRepository.class,
	ProyectoRepository.class,
	TipoItemRepository.class, 
	EstadoRepository.class, 
	ConfiguracionItemRepository.class,
	ItemRepository.class,
	PrioridadRepository.class
})
@Transactional
public class TestItem {

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
	ItemService iService;
	
	@Autowired
	PrioridadService prService;
	
	@Test
	@Rollback
	public void test() {
		
		try {
			Persona i = new Persona();
			i.setNombre("Jefi");
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
			
			ConfiguracionEstado confEstado = new ConfiguracionEstado();
			ConfiguracionItem ci = new ConfiguracionItem();
			ci.getProximosEstados().put(e, confEstado);
			ci.setTipoItem(ti);
			confEstado.setConfiguracionItem(ci);
			//ciService.save(ci);
			
			Proyecto p = new Proyecto();
			p.setLider(i);
			p.setNombre("ppp2");
			p.getTipoItems().put(ti, ci);
			p.getIntegrantes().add(i);
			ci.setProyecto(p);
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
