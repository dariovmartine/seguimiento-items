package edu.unlp.informatica.postgrado.seguimiento.item;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import edu.unlp.informatica.postgrado.seguimiento.AppConfig;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Item;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Persona;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Prioridad;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;
import static edu.unlp.informatica.postgrado.seguimiento.item.model.TipoEstado.*;
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
	
	Persona per;
	Prioridad pr;
	TipoItem ti;
	Estado e1, e2, e3;
	ConfiguracionEstado confEstado;
	Proyecto p;
	
	Item i;
	
	@Before
	public void setUp(){
		try {
			per = new Persona();
			per.setNombre("Jefi");
			per.setUserName("test");
			per.setHabilitado(true);
			per.setPassword("sss");
			myService.save(per);
			
			pr = new Prioridad();
			pr.setNombre("Altisi3");
			prService.save(pr);
						
			ti = new TipoItem();
			ti.setNombre("Ampliació3");
			tiService.save(ti);
			
			e1 = new Estado();
			e1.setNombre("inicial");
			e1.setTipoEstado(INICIAL);
			eService.save(e1);
			
			e2 = new Estado();
			e2.setNombre("en proceso");
			e2.setTipoEstado(INTERMEDIO);
			eService.save(e2);
			
			e3 = new Estado();
			e3.setNombre("finalizado");
			e3.setTipoEstado(FINAL);
			eService.save(e3);
			
			confEstado = new ConfiguracionEstado();
			ConfiguracionItem ci = new ConfiguracionItem();
			ci.getProximosEstados().put(e1, confEstado);
			ci.setTipoItem(ti);
			confEstado.setConfiguracionItem(ci);
			confEstado.getProximosEstados().add(e2);
			//ciService.save(ci);
			
			i = new Item();
			i.cambiarEstado(e1, null, "estado inicial");
			i.setFechaCarga(Timestamp.valueOf("2012-02-02 10:00:00"));
			i.setTipoItem(ti);
			i.setTitulo("prueba");
			i.setDescripcion("descrip");
			i.setPrioridad(pr);
			i.setResponsable(per);
			
			p = new Proyecto();
			p.setLider(per);
			p.setNombre("ppp2");
			p.getTipoItems().put(ti, ci);
			p.getIntegrantes().add(per);
			ci.setProyecto(p);
			
			proService.save(p);
			i.setProyecto(p);
			iService.save(i);
				
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	@Rollback
	public void test() {
		
		try {
			
			Item i1 = new Item();
			i1.setTitulo("lu3333");
			i1.setProyecto(p);
			i1.setPrioridad(pr);
			i1.setDescripcion("ss");
			i1.setResponsable(per);
			i1.setTipoItem(ti);
			i1.setEstado(e1);
			iService.save(i1);
			
			
			//assertTrue("Debería haberse grabado algo.", myService.find().size() > 0);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Rollback
	public void tetsCambioEstadook() {
		try {
			int cantHistInicial, cantHistFinal;
			cantHistInicial = i.getHistorial().size();
			i.cambiarEstado(e2, per, "probando cambio de estado");
			iService.save(i);
			cantHistFinal = i.getHistorial().size();
			
			assertTrue("Debería habercambiado de estado", i.getEstado().equals(e2) && cantHistInicial == cantHistFinal-1);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
